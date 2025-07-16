package controller;

import model.Utilisateur;
import model.Adherent;
import service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repository.AdherentRepository;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private AdherentRepository adherentRepository;

    @GetMapping("/creer")
    public String showCreateForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("adherents", adherentRepository.findAll());
        return "creerUtilisateur";
    }

    @PostMapping("/creer")
    public String creerUtilisateur(@ModelAttribute Utilisateur utilisateur, @RequestParam("adherentId") Integer adherentId, Model model) {
        Adherent adherent = adherentRepository.findById(adherentId).orElse(null);
        if (adherent == null) {
            model.addAttribute("message", "Adhérent introuvable");
            model.addAttribute("adherents", adherentRepository.findAll());
            return "creerUtilisateur";
        }
        utilisateur.setAdherent(adherent);
        utilisateurService.creerUtilisateur(utilisateur);
        model.addAttribute("message", "Utilisateur créé avec succès");
        model.addAttribute("adherents", adherentRepository.findAll());
        return "creerUtilisateur";
    }
}
