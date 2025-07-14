package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import service.EmpruntService;

@Controller
@RequestMapping("/emprunt")
public class EmpruntController {

    @Autowired
    private EmpruntService empruntService;

    // Vérifie si l'utilisateur est un bibliothécaire
    private boolean isBibliothecaire(HttpSession session, RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("BIBLIOTHECAIRE")) {
            redirectAttributes.addFlashAttribute("erreur", "Accès non autorisé.");
            return false;
        }
        return true;
    }

    // Affiche le formulaire d'emprunt (empruntForm.jsp)
    @GetMapping("/form")
    public String empruntForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        model.addAttribute("exemplaires", empruntService.getExemplairesDisponibles());
        return "empruntForm"; // Compatible avec empruntForm.jsp
    }

    // Effectue un emprunt
    @PostMapping("/effectuer")
    public String effectuerEmprunt(
            @RequestParam int membreId,
            @RequestParam int exemplaireId,
            @RequestParam String typePret,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            boolean success = empruntService.effectuerEmprunt(membreId, exemplaireId, typePret);
            redirectAttributes.addFlashAttribute("message", success ? "Emprunt effectué avec succès." : "Erreur lors de l'emprunt.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur : membre ou exemplaire invalide.");
        }
        return "redirect:/emprunt/form";
    }
}