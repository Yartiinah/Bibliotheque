package controller;

import model.Pret;
import model.Exemplaire;
import model.Adherent;
import repository.ExemplaireRepository;
import repository.AdherentRepository;
import service.PretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/prets")
public class PretController {
    @Autowired
    private PretService pretService;
    @Autowired
    private ExemplaireRepository exemplaireRepository;
    @Autowired
    private AdherentRepository adherentRepository;

    @GetMapping("/nouveau")
    public String showForm(Model model, HttpSession session) {
        model.addAttribute("exemplaires", exemplaireRepository.findAll());
        model.addAttribute("adherents", adherentRepository.findAll());
        Object userObj = session.getAttribute("user");
        if (userObj == null || !"BIBLIOTHECAIRE".equals(((model.Utilisateur)userObj).getRole())) {
            return "redirect:/login";
        }
        return "ajouterPret";
    }

    @PostMapping("/nouveau")
    public String creerPret(@RequestParam("adherentId") Integer adherentId,
                           @RequestParam("referenceExemplaire") String referenceExemplaire,
                           @RequestParam("typePret") String typePret,
                           @RequestParam(value = "dateEmprunt") String dateEmprunt,
                           @RequestParam(value = "reservationId", required = false) Integer reservationId,
                           Model model, HttpSession session) {
        Object userObj = session.getAttribute("user");
        if (userObj == null || !"BIBLIOTHECAIRE".equals(((model.Utilisateur)userObj).getRole())) {
            return "redirect:/login";
        }
        String message = pretService.creerPret(adherentId, referenceExemplaire, typePret, dateEmprunt, reservationId);
        model.addAttribute("message", message);
        model.addAttribute("exemplaires", exemplaireRepository.findAll());
        model.addAttribute("adherents", adherentRepository.findAll());
        return "ajouterPret";
    }

    @GetMapping("/liste")
    public String listePrets(Model model, HttpSession session) {
        model.Utilisateur user = (model.Utilisateur) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Pret> prets;
        if ("BIBLIOTHECAIRE".equals(user.getRole())) {
            prets = pretService.pretRepository.findAll();
            model.addAttribute("isBibliothecaire", true);
        } else if (user.getAdherent() != null) {
            Integer adherentId = user.getAdherent().getId();
            prets = pretService.getPretsByAdherentId(adherentId);
            model.addAttribute("isBibliothecaire", false);
        } else {
            return "redirect:/login";
        }
        model.addAttribute("prets", prets);
        return "listePrets";
    }

    @GetMapping("/pretsAdherent")
    public String pretsAdherent(@RequestParam("adherentId") Integer adherentId, Model model, HttpSession session) {
        Object userObj = session.getAttribute("user");
        if (userObj == null || !"BIBLIOTHECAIRE".equals(((model.Utilisateur)userObj).getRole())) {
            return "redirect:/login";
        }
        Adherent adherent = adherentRepository.findById(adherentId).orElse(null);
        if (adherent != null) {
            List<Pret> prets = pretService.pretRepository.findByAdherentId(adherentId);
            model.addAttribute("adherent", adherent);
            model.addAttribute("prets", prets);
        } else {
            model.addAttribute("message", "Adhérent introuvable.");
        }
        return "pretsAdherent";
    }

    @GetMapping("/rechercher")
    public String rechercherPret(@RequestParam(value = "referenceExemplaire", required = false) String reference,
                                @RequestParam(value = "dateEmprunt", required = false) String dateEmprunt,
                                Model model, HttpSession session) {
        Object userObj = session.getAttribute("user");
        if (userObj == null || !"BIBLIOTHECAIRE".equals(((model.Utilisateur)userObj).getRole())) {
            return "redirect:/login";
        }
        model.addAttribute("exemplaires", exemplaireRepository.findAll());
        model.addAttribute("referenceExemplaire", reference);
        model.addAttribute("dateEmprunt", dateEmprunt);
        if (reference != null && !reference.trim().isEmpty()) {
            Exemplaire exemplaire = exemplaireRepository.findByReference(reference).orElse(null);
            if (exemplaire != null) {
                Pret pret = pretService.findPretByExemplaireAndDate(reference, dateEmprunt);
                if (pret != null) {
                    model.addAttribute("pret", pret);
                } else {
                    model.addAttribute("message", "Aucun prêt trouvé pour cet exemplaire" + (dateEmprunt != null ? " à la date spécifiée." : "."));
                }
            } else {
                model.addAttribute("message", "Exemplaire introuvable.");
            }
        }
        return "rechercherPret";
    }

    @PostMapping("/rechercher")
    public String rechercherPretPost(@RequestParam(value = "referenceExemplaire") String reference,
                                    @RequestParam(value = "dateEmprunt", required = false) String dateEmprunt,
                                    Model model, HttpSession session) {
        return rechercherPret(reference, dateEmprunt, model, session);
    }

    @PostMapping("/modifier-date-retour-effective")
    public String modifierDateRetourEffective(@RequestParam("pretId") Integer pretId,
                                             @RequestParam("nouvelleDateRetourEffective") String nouvelleDateRetourEffective,
                                             Model model, HttpSession session) {
        Object userObj = session.getAttribute("user");
        if (userObj == null || !"BIBLIOTHECAIRE".equals(((model.Utilisateur)userObj).getRole())) {
            return "redirect:/login";
        }
        try {
            String message = pretService.modifierDateRetourEffective(pretId, nouvelleDateRetourEffective);
            model.addAttribute("message", message);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("exemplaires", exemplaireRepository.findAll());
        Pret pret = pretService.pretRepository.findById(pretId).orElse(null);
        if (pret != null) {
            model.addAttribute("pret", pret);
        }
        return "rechercherPret";
    }

    @GetMapping("/retour")
    public String retourPret(@RequestParam("id") Integer idPret, Model model, HttpSession session) {
        Object userObj = session.getAttribute("user");
        if (userObj == null || !"BIBLIOTHECAIRE".equals(((model.Utilisateur)userObj).getRole())) {
            return "redirect:/login";
        }
        Pret pret = pretService.pretRepository.findById(idPret).orElse(null);
        if (pret != null) {
            String message = pretService.retourPret(idPret);
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", "Prêt introuvable.");
        }
        model.addAttribute("exemplaires", exemplaireRepository.findAll());
        return "rechercherPret";
    }

    @GetMapping("/mes-prets")
    public String mesPrets(Model model, HttpSession session) {
        model.Utilisateur user = (model.Utilisateur) session.getAttribute("user");
        if (user == null || user.getAdherent() == null) {
            return "redirect:/login";
        }
        Integer adherentId = user.getAdherent().getId();
        List<Pret> prets = pretService.getPretsByAdherentId(adherentId);
        model.addAttribute("prets", prets);
        model.addAttribute("isBibliothecaire", false);
        return "listePrets";
    }

    @PostMapping("/prolonger")
    public String prolongerPretDansListe(@RequestParam("pretId") Integer pretId,
                                        @RequestParam("nouvelleDateRetourPrevue") String nouvelleDateRetourPrevue,
                                        Model model, HttpSession session) {
        model.Utilisateur user = (model.Utilisateur) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        try {
            String message;
            if ("BIBLIOTHECAIRE".equals(user.getRole())) {
                message = pretService.prolongerPret(pretId, null, nouvelleDateRetourPrevue);
                List<Pret> prets = pretService.pretRepository.findAll();
                model.addAttribute("prets", prets);
                model.addAttribute("isBibliothecaire", true);
            } else if (user.getAdherent() != null) {
                Integer adherentId = user.getAdherent().getId();
                message = pretService.prolongerPret(pretId, adherentId, nouvelleDateRetourPrevue);
                List<Pret> prets = pretService.getPretsByAdherentId(adherentId);
                model.addAttribute("prets", prets);
                model.addAttribute("isBibliothecaire", false);
            } else {
                return "redirect:/login";
            }
            model.addAttribute("message", message);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            List<Pret> prets = "BIBLIOTHECAIRE".equals(user.getRole()) ?
                    pretService.pretRepository.findAll() :
                    pretService.getPretsByAdherentId(user.getAdherent().getId());
            model.addAttribute("prets", prets);
            model.addAttribute("isBibliothecaire", "BIBLIOTHECAIRE".equals(user.getRole()));
        }
        return "listePrets";
    }

    @GetMapping("/ajouter")
    public String ajouterPretDepuisReservation(@RequestParam(value = "adherentId", required = false) Integer adherentId,
                                              @RequestParam(value = "referenceExemplaire", required = false) String referenceExemplaire,
                                              @RequestParam(value = "dateEmprunt", required = false) String dateEmprunt,
                                              @RequestParam(value = "reservationId", required = false) Integer reservationId,
                                              Model model, HttpSession session) {
        model.addAttribute("exemplaires", exemplaireRepository.findAll());
        model.addAttribute("adherents", adherentRepository.findAll());
        if (adherentId != null) model.addAttribute("adherentId", adherentId);
        if (referenceExemplaire != null) model.addAttribute("referenceExemplaire", referenceExemplaire);
        if (dateEmprunt != null) model.addAttribute("dateEmprunt", dateEmprunt);
        if (reservationId != null) model.addAttribute("reservationId", reservationId);
        Object userObj = session.getAttribute("user");
        if (userObj == null || !"BIBLIOTHECAIRE".equals(((model.Utilisateur)userObj).getRole())) {
            return "redirect:/login";
        }
        return "ajouterPret";
    }
}