package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import service.ProlongationService;

@Controller
@RequestMapping("/prolongation")
public class ProlongationController {

    @Autowired
    private ProlongationService prolongationService;

    private boolean isMembre(HttpSession session) {
        Map<String, Object> membre = (Map<String, Object>) session.getAttribute("membreConnecte");
        return membre != null;
    }

    private boolean isBibliothecaire(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "BIBLIOTHECAIRE".equals(role);
    }

    @PostMapping("/demander")
    public String demanderProlongation(@RequestParam int pretId, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isMembre(session)) {
            return "redirect:/membre/login-form";
        }
        Map<String, Object> membre = (Map<String, Object>) session.getAttribute("membreConnecte");
        int membreId = (Integer) membre.get("id");
        boolean success = prolongationService.demanderProlongation(pretId, membreId);
        redirectAttributes.addFlashAttribute("message", success ? "Demande de prolongation soumise." : "Erreur lors de la demande.");
        return "redirect:/membre/espace";
    }

    @GetMapping("/gestion")
    public String gestionProlongations(HttpSession session, Model model) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        model.addAttribute("prolongations", prolongationService.getDemandesProlongation());
        return "gestionProlongations";
    }

    @PostMapping("/traiter")
    public String traiterProlongation(@RequestParam int pretId, @RequestParam boolean accepter,
                                     HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        boolean success = prolongationService.traiterProlongation(pretId, accepter);
        redirectAttributes.addFlashAttribute("message", success ? "Prolongation traitée." : "Erreur lors du traitement.");
        return "redirect:/prolongation/gestion";
    }
}