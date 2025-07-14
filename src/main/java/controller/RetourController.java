package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import service.RetourService;

@Controller
@RequestMapping("/retour")
public class RetourController {

    @Autowired
    private RetourService retourService;

    // Vérifie si l'utilisateur est un bibliothécaire
    private boolean isBibliothecaire(HttpSession session, RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("BIBLIOTHECAIRE")) {
            redirectAttributes.addFlashAttribute("erreur", "Accès non autorisé.");
            return false;
        }
        return true;
    }

    // Affiche le formulaire de retour (retourForm.jsp)
    @GetMapping("/form")
    public String retourForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        model.addAttribute("prets", retourService.getPretsEnCours());
        return "retourForm"; // Compatible avec retourForm.jsp
    }

    // Enregistre un retour
    @PostMapping("/enregistrer")
    public String enregistrerRetour(
            @RequestParam int pretId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            retourService.enregistrerRetour(pretId);
            redirectAttributes.addFlashAttribute("message", "Retour enregistré avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de l'enregistrement du retour.");
        }
        return "redirect:/retour/form";
    }
}