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

    private boolean isBibliothecaire(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "BIBLIOTHECAIRE".equals(role);
    }

    @GetMapping("/form")
    public String afficherFormulaireRetour(HttpSession session, Model model) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        model.addAttribute("prets", retourService.getPretsEnCours());
        return "retourForm";
    }

    @PostMapping("/enregistrer")
    public String enregistrerRetour(@RequestParam int pretId, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        boolean success = retourService.enregistrerRetour(pretId);
        redirectAttributes.addFlashAttribute("message", success ? "Retour enregistré." : "Erreur lors du retour.");
        return "redirect:/retour/form";
    }
}