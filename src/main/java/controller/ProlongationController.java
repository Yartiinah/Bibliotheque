package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import service.ProlongationService;

import java.util.Map;

@Controller
@RequestMapping("/prolongation")
public class ProlongationController {

    @Autowired
    private ProlongationService prolongationService;

    // Vérifie si l'utilisateur est un bibliothécaire
    private boolean isBibliothecaire(HttpSession session, RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("BIBLIOTHECAIRE")) {
            redirectAttributes.addFlashAttribute("erreur", "Accès non autorisé.");
            return false;
        }
        return true;
    }

    // Demande une prolongation (depuis espaceMembre.jsp)
    @PostMapping("/demander")
    public String demanderProlongation(
            @RequestParam int pretId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Map<String, Object> membre = (Map<String, Object>) session.getAttribute("membreConnecte");
        if (membre == null) {
            redirectAttributes.addFlashAttribute("erreur", "Veuillez vous connecter.");
            return "redirect:/membre/login-form";
        }
        try {
            boolean success = prolongationService.demanderProlongation(pretId, (Integer) membre.get("id"));
            redirectAttributes.addFlashAttribute("message", success ? "Demande de prolongation soumise." : "Erreur lors de la demande.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur : prêt invalide ou prolongation non autorisée.");
        }
        return "redirect:/membre/espace";
    }

    // Affiche la gestion des prolongations (gestionProlongations.jsp)
    @GetMapping("/gestion")
    public String gestionProlongations(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        model.addAttribute("prolongations", prolongationService.getDemandesProlongation());
        return "gestionProlongations"; // Compatible avec gestionProlongations.jsp
    }

    // Traite une prolongation
    @PostMapping("/traiter")
    public String traiterProlongation(
            @RequestParam int pretId,
            @RequestParam boolean accepter,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            prolongationService.traiterProlongation(pretId, accepter);
            redirectAttributes.addFlashAttribute("message", accepter ? "Prolongation acceptée." : "Prolongation refusée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors du traitement de la prolongation.");
        }
        return "redirect:/prolongation/gestion";
    }
}