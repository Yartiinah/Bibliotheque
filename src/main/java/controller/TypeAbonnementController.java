package controller;

import model.TypeAbonnement;
import service.TypeAbonnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/types-abonnement")
public class TypeAbonnementController {
    @Autowired
    private TypeAbonnementService typeAbonnementService;

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        TypeAbonnement abo = typeAbonnementService.getById(id);
        model.addAttribute("typeAbonnement", abo);
        return "editTypeAbonnement";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable int id,
                         @RequestParam String libelle,
                         @RequestParam double tarif,
                         @RequestParam int quotaLivre,
                         @RequestParam int dureePretJour,
                         @RequestParam int quotaReservation,
                         @RequestParam int quotaProlongement,
                         @RequestParam int nbJourProlongement,
                         Model model) {
        TypeAbonnement abo = typeAbonnementService.updateTypeAbonnement(id, libelle, tarif, quotaLivre, dureePretJour, quotaReservation, quotaProlongement, nbJourProlongement);
        model.addAttribute("typeAbonnement", abo);
        model.addAttribute("message", "Type d'abonnement modifié avec succès !");
        return "editTypeAbonnement";
    }
}