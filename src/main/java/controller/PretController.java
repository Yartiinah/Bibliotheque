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
    // Vérification du rôle
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
    // Vérification du rôle
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
    // Vérification du rôle
    Object userObj = session.getAttribute("user");
    if (userObj == null || !"BIBLIOTHECAIRE".equals(((model.Utilisateur)userObj).getRole())) {
        return "redirect:/login";
    }
    List<Pret> prets = pretService.pretRepository.findAll();
    model.addAttribute("prets", prets);
    return "listePrets";
}

@GetMapping("/pretsAdherent")
public String pretsAdherent(@RequestParam("adherentId") Integer adherentId, Model model, HttpSession session) {
    // Vérification du rôle
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
public String rechercherPret(@RequestParam(value = "referenceExemplaire", required = false) String reference, Model model) {
    model.addAttribute("exemplaires", exemplaireRepository.findAll());
    if(reference != null){
        Exemplaire exemplaire = exemplaireRepository.findByReference(reference).orElse(null);
        if(exemplaire != null){
            Pret pret = pretService.pretRepository.findByExemplaireReferenceAndStatut(reference, "en_cours");
            if(pret != null){
                model.addAttribute("pret", pret);
            } else {
                model.addAttribute("message", "Aucun prêt en cours pour cet exemplaire.");
            }
        } else {
            model.addAttribute("message", "Exemplaire introuvable.");
        }
    }
    return "rechercherPret";
}

@GetMapping("/retour")
public String retourPret(@RequestParam("id") Integer idPret, Model model) {
    Pret pret = pretService.pretRepository.findById(idPret).orElse(null);
    if (pret != null) {
        String message = pretService.retourPret(idPret);
        model.addAttribute("message", message);
    } else {
        model.addAttribute("message", "Prêt introuvable.");
    }
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
    return "mesPrets";
}

// @GetMapping("/prolonger")
// public String prolongerPret(@RequestParam("id") Integer idPret, Model model, HttpSession session) {
//     model.Utilisateur user = (model.Utilisateur) session.getAttribute("user");
//     if (user == null || user.getAdherent() == null) {
//         return "redirect:/login";
//     }
//     Integer adherentId = user.getAdherent().getId();
//     String message = pretService.prolongerPret(idPret, adherentId);
//     model.addAttribute("message", message);
//     return "mesPrets";
// }

@GetMapping("/prolonger")
public String prolongerPret(@RequestParam("id") Integer idPret, Model model, HttpSession session) {
    model.Utilisateur user = (model.Utilisateur) session.getAttribute("user");
    if (user == null || user.getAdherent() == null) {
        return "redirect:/login";
    }
    Integer adherentId = user.getAdherent().getId();
    String message = pretService.prolongerPret(idPret, adherentId); 
    List<Pret> prets = pretService.getPretsByAdherentId(adherentId);
    model.addAttribute("prets", prets);
    model.addAttribute("message", message);
    return "mesPrets";
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
    // Vérification du rôle
    Object userObj = session.getAttribute("user");
    if (userObj == null || !"BIBLIOTHECAIRE".equals(((model.Utilisateur)userObj).getRole())) {
        return "redirect:/login";
    }
    return "ajouterPret";
}
}