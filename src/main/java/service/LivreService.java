// src/main/java/com/example/mylibrary/controller/LivreController.java
package com.example.mylibrary.controller;

import com.example.mylibrary.model.Livre;
import com.example.mylibrary.service.LivreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/livres")
public class LivreController {

    private final LivreService livreService;

    // Injection de dépendance du LivreService
    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    // Affiche la liste de tous les livres
    @GetMapping
    public String getAllLivres(Model model) {
        List<Livre> livres = livreService.getAllLivres();
        model.addAttribute("livres", livres);
        return "listeLivres"; // Nom de la vue JSP pour afficher la liste des livres
    }

    // Affiche le formulaire pour ajouter un nouveau livre
    @GetMapping("/ajouter")
    public String showAddForm(Model model) {
        model.addAttribute("livre", new Livre()); // Objet vide pour le formulaire
        // Vous aurez probablement besoin d'une liste de catégories ici
        // model.addAttribute("categories", categorieService.getAllCategories());
        return "livreForm"; // Nom de la vue JSP pour le formulaire d'ajout/modification
    }

    // Traite la soumission du formulaire d'ajout de livre
    @PostMapping("/ajouter")
    public String addLivre(@ModelAttribute Livre livre, RedirectAttributes redirectAttributes) {
        if (livreService.createLivre(livre)) {
            redirectAttributes.addFlashAttribute("message", "Livre ajouté avec succès!");
        } else {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de l'ajout du livre.");
        }
        return "redirect:/livres";
    }

    // Affiche le formulaire pour modifier un livre existant
    @GetMapping("/modifier/{id}")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Livre> livre = livreService.getLivreById(id);
        if (livre.isPresent()) {
            model.addAttribute("livre", livre.get());
            // Vous aurez probablement besoin d'une liste de catégories ici
            // model.addAttribute("categories", categorieService.getAllCategories());
            return "livreForm"; // Nom de la vue JSP pour le formulaire d'ajout/modification
        } else {
            redirectAttributes.addFlashAttribute("erreur", "Livre non trouvé.");
            return "redirect:/livres";
        }
    }

    // Traite la soumission du formulaire de modification de livre
    @PostMapping("/modifier")
    public String updateLivre(@ModelAttribute Livre livre, RedirectAttributes redirectAttributes) {
        if (livreService.updateLivre(livre)) {
            redirectAttributes.addFlashAttribute("message", "Livre mis à jour avec succès!");
        } else {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de la mise à jour du livre.");
        }
        return "redirect:/livres";
    }

    // Supprime un livre
    @PostMapping("/supprimer/{id}")
    public String deleteLivre(@PathVariable int id, RedirectAttributes redirectAttributes) {
        if (livreService.deleteLivre(id)) {
            redirectAttributes.addFlashAttribute("message", "Livre supprimé avec succès!");
        } else {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de la suppression du livre.");
        }
        return "redirect:/livres";
    }
}