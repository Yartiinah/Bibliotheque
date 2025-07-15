package controller;

import model.Livre;
import model.Categorie;
import service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/livres")
public class LivreController {
    @Autowired
    private LivreService livreService;

    @GetMapping("/ajouter")
    public String showForm(Model model) {
        model.addAttribute("livre", new Livre());
        model.addAttribute("categories", livreService.getAllCategories());
        return "ajouterLivre";
    }

    @PostMapping("/ajouter")
    public String ajouterLivre(@ModelAttribute("livre") Livre livre, Model model) {
        Integer categorieId = livre.getCategorie() != null ? livre.getCategorie().getId() : null;
        Livre saved = livreService.ajouterLivre(
            livre.getTitre(),
            livre.getAuteur(),
            categorieId,
            livre.getIsbn(),
            livre.getRestriction()
        );
        model.addAttribute("message", saved != null ? "Livre ajouté avec succès" : "Erreur lors de l'ajout");
        model.addAttribute("categories", livreService.getAllCategories());
        return "ajouterLivre";
    }

    @GetMapping("/liste")
    public String listeLivres(Model model) {
        model.addAttribute("livres", livreService.getAllLivres());
        return "listeLivres";
    }
}
