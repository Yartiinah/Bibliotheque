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

    private boolean isBibliothecaire(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "BIBLIOTHECAIRE".equals(role);
    }

    @GetMapping("/form")
    public String afficherFormulaireEmprunt(HttpSession session, Model model) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        model.addAttribute("exemplaires", empruntService.getExemplairesDisponibles());
        return "empruntForm";
    }

    @PostMapping("/effectuer")
    public String effectuerEmprunt(
            @RequestParam int membreId,
            @RequestParam int exemplaireId,
            @RequestParam String typePret,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        Map<String, Object> membreInfo = empruntService.getMembreInfo(membreId);
        if (membreInfo == null) {
            redirectAttributes.addFlashAttribute("erreur", "Membre non trouvé ou non valide.");
            return "redirect:/emprunt/form";
        }
        if (empruntService.aPenaliteEnCours(membreId)) {
            redirectAttributes.addFlashAttribute("erreur", "Le membre a une pénalité en cours.");
            return "redirect:/emprunt/form";
        }
        boolean success = empruntService.effectuerEmprunt(membreId, exemplaireId, typePret);
        redirectAttributes.addFlashAttribute("message", success ? "Emprunt effectué avec succès." : "Erreur lors de l'emprunt.");
        return "redirect:/emprunt/form";
    }
}