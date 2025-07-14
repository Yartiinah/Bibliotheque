package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import service.BibliothecaireService;
import service.MembreService;

@Controller
@RequestMapping("/bibliothecaire")
public class BibliothecaireController {

    @Autowired
    private MembreService membreService;

    @Autowired
    private BibliothecaireService bibliothecaireService;

    private boolean isBibliothecaire(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "BIBLIOTHECAIRE".equals(role);
    }

    @GetMapping("/gestion-adherent")
    public String gestionAdherent(HttpSession session, Model model) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        model.addAttribute("inscriptions", membreService.getInscriptionsEnAttente());
        return "gestionAdherent";
    }

    @PostMapping("/accepter-inscription")
    public String accepterInscription(@RequestParam int id, @RequestParam double montantCotisation,
                                     HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        boolean success = membreService.accepterInscription(id, montantCotisation);
        redirectAttributes.addFlashAttribute("message", success ? "Inscription acceptée." : "Erreur lors de l'acceptation.");
        return "redirect:/bibliothecaire/gestion-adherent";
    }

    @PostMapping("/refuser-inscription")
    public String refuserInscription(@RequestParam int id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        boolean success = membreService.refuserInscription(id);
        redirectAttributes.addFlashAttribute("message", success ? "Inscription refusée." : "Erreur lors du refus.");
        return "redirect:/bibliothecaire/gestion-adherent";
    }

    @GetMapping("/gestion-categorie")
    public String gestionCategorie(HttpSession session, Model model) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        model.addAttribute("categories", bibliothecaireService.getAllCategories());
        return "gestionCategorie";
    }

    @PostMapping("/ajout-categorie")
    public String ajouterCategorie(@RequestParam String nom, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        boolean success = bibliothecaireService.createCategorie(nom);
        redirectAttributes.addFlashAttribute("message", success ? "Catégorie ajoutée." : "Erreur lors de l'ajout.");
        return "redirect:/bibliothecaire/gestion-categorie";
    }

    @PostMapping("/modifier-categorie")
    public String modifierCategorie(@RequestParam int id, @RequestParam String nom,
                                   HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        boolean success = bibliothecaireService.updateCategorie(id, nom);
        redirectAttributes.addFlashAttribute("message", success ? "Catégorie modifiée." : "Erreur lors de la modification.");
        return "redirect:/bibliothecaire/gestion-categorie";
    }

    @PostMapping("/supprimer-categorie")
    public String supprimerCategorie(@RequestParam int id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        boolean success = bibliothecaireService.deleteCategorie(id);
        redirectAttributes.addFlashAttribute("message", success ? "Catégorie supprimée." : "Erreur lors de la suppression.");
        return "redirect:/bibliothecaire/gestion-categorie";
    }

    @GetMapping("/gestion-livre")
    public String gestionLivre(HttpSession session, Model model) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        model.addAttribute("livres", bibliothecaireService.getAllLivres());
        model.addAttribute("categories", bibliothecaireService.getAllCategories());
        return "gestionLivre";
    }

    @PostMapping("/ajout-livre")
    public String ajouterLivre(@RequestParam String titre, @RequestParam String auteur, @RequestParam int categorieId,
                              HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        boolean success = bibliothecaireService.createLivre(titre, auteur, categorieId);
        redirectAttributes.addFlashAttribute("message", success ? "Livre ajouté." : "Erreur lors de l'ajout.");
        return "redirect:/bibliothecaire/gestion-livre";
    }

    @PostMapping("/modifier-livre")
    public String modifierLivre(@RequestParam int id, @RequestParam String titre, @RequestParam String auteur,
                                @RequestParam int categorieId, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        boolean success = bibliothecaireService.updateLivre(id, titre, auteur, categorieId);
        redirectAttributes.addFlashAttribute("message", success ? "Livre modifié." : "Erreur lors de la modification.");
        return "redirect:/bibliothecaire/gestion-livre";
    }

    @PostMapping("/supprimer-livre")
    public String supprimerLivre(@RequestParam int id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        boolean success = bibliothecaireService.deleteLivre(id);
        redirectAttributes.addFlashAttribute("message", success ? "Livre supprimé." : "Erreur lors de la suppression.");
        return "redirect:/bibliothecaire/gestion-livre";
    }
}