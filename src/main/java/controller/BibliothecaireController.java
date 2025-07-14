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

    // Vérifie si l'utilisateur est un bibliothécaire
    private boolean isBibliothecaire(HttpSession session, RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("BIBLIOTHECAIRE")) {
            redirectAttributes.addFlashAttribute("erreur", "Accès non autorisé.");
            return false;
        }
        return true;
    }

    // Affiche la gestion des adhésions (gestionAdherent.jsp)
    @GetMapping("/gestion-adherent")
    public String gestionAdherent(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        model.addAttribute("inscriptions", membreService.getInscriptionsEnAttente());
        return "gestionAdherent";
    }

    // Accepte une inscription
    @PostMapping("/accepter-inscription")
    public String accepterInscription(
            @RequestParam int id,
            @RequestParam double montantCotisation,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            membreService.validerInscription(id, montantCotisation);
            redirectAttributes.addFlashAttribute("message", "Inscription validée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de la validation de l'inscription.");
        }
        return "redirect:/bibliothecaire/gestion-adherent";
    }

    // Refuse une inscription
    @PostMapping("/refuser-inscription")
    public String refuserInscription(
            @RequestParam int id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            membreService.refuserInscription(id);
            redirectAttributes.addFlashAttribute("message", "Inscription refusée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors du refus de l'inscription.");
        }
        return "redirect:/bibliothecaire/gestion-adherent";
    }

    // Affiche la gestion des catégories (gestionCategorie.jsp)
    @GetMapping("/gestion-categorie")
    public String gestionCategorie(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        model.addAttribute("categories", bibliothecaireService.getAllCategories());
        return "gestionCategorie";
    }

    // Ajoute une catégorie
    @PostMapping("/ajout-categorie")
    public String ajoutCategorie(
            @RequestParam String nom,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            bibliothecaireService.ajouterCategorie(nom);
            redirectAttributes.addFlashAttribute("message", "Catégorie ajoutée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de l'ajout de la catégorie.");
        }
        return "redirect:/bibliothecaire/gestion-categorie";
    }

    // Modifie une catégorie
    @PostMapping("/modifier-categorie")
    public String modifierCategorie(
            @RequestParam int id,
            @RequestParam String nom,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            bibliothecaireService.modifierCategorie(id, nom);
            redirectAttributes.addFlashAttribute("message", "Catégorie modifiée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de la modification de la catégorie.");
        }
        return "redirect:/bibliothecaire/gestion-categorie";
    }

    // Supprime une catégorie
    @PostMapping("/supprimer-categorie")
    public String supprimerCategorie(
            @RequestParam int id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            bibliothecaireService.supprimerCategorie(id);
            redirectAttributes.addFlashAttribute("message", "Catégorie supprimée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de la suppression de la catégorie.");
        }
        return "redirect:/bibliothecaire/gestion-categorie";
    }

    // Affiche la gestion des livres (gestionLivre.jsp)
    @GetMapping("/gestion-livre")
    public String gestionLivre(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        model.addAttribute("livres", bibliothecaireService.getAllLivres());
        model.addAttribute("categories", bibliothecaireService.getAllCategories());
        return "gestionLivre";
    }

    // Ajoute un livre
    @PostMapping("/ajout-livre")
    public String ajoutLivre(
            @RequestParam String titre,
            @RequestParam String auteur,
            @RequestParam int categorieId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            bibliothecaireService.ajouterLivre(titre, auteur, categorieId);
            redirectAttributes.addFlashAttribute("message", "Livre ajouté avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de l'ajout du livre.");
        }
        return "redirect:/bibliothecaire/gestion-livre";
    }

    // Modifie un livre
    @PostMapping("/modifier-livre")
    public String modifierLivre(
            @RequestParam int id,
            @RequestParam String titre,
            @RequestParam String auteur,
            @RequestParam int categorieId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            bibliothecaireService.modifierLivre(id, titre, auteur, categorieId);
            redirectAttributes.addFlashAttribute("message", "Livre modifié avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de la modification du livre.");
        }
        return "redirect:/bibliothecaire/gestion-livre";
    }

    // Supprime un livre
    @PostMapping("/supprimer-livre")
    public String supprimerLivre(
            @RequestParam int id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            bibliothecaireService.supprimerLivre(id);
            redirectAttributes.addFlashAttribute("message", "Livre supprimé avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de la suppression du livre.");
        }
        return "redirect:/bibliothecaire/gestion-livre";
    }
}