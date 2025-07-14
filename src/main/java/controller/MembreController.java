package controller;

import model.TypeAbonnement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import service.MembreService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
@RequestMapping("/membre")
public class MembreController {

    @Autowired
    private MembreService membreService;

    @GetMapping("/inscription-form-en-ligne")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("profils", LibelleAbonnement.values());
        return "inscriptionForm";
    }

    @GetMapping("/login-form")
    public String afficherFormulaireLogin() {
        return "loginMembre";
    }

    @PostMapping("/inscription-en-ligne")
    public String traiterInscription(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String email,
            @RequestParam String adresse,
            @RequestParam String dateNaissance,
            @RequestParam String profil,
            RedirectAttributes redirectAttributes) {
        try {
            LocalDateTime date = LocalDateTime.parse(dateNaissance, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            TypeAbonnement typeAbonnement = TypeAbonnement.valueOf(profil.toUpperCase());
            boolean success = membreService.inscriptionADistance(nom, prenom, email, adresse, date, typeAbonnement);
            redirectAttributes.addFlashAttribute("message", success ? "Inscription soumise avec succès, en attente de validation." : "Erreur lors de l'inscription.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Date invalide ou erreur lors de l'inscription.");
        }
        return "redirect:/membre/inscription-form-en-ligne";
    }

    @PostMapping("/login")
    public String loginMembre(
            @RequestParam String email,
            @RequestParam String nom,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Map<String, Object> membre = membreService.loginMembre(email, nom);
        if (membre != null) {
            session.setAttribute("membreConnecte", membre);
            return "redirect:/membre/espace";
        } else {
            redirectAttributes.addFlashAttribute("erreur", "Email ou nom incorrect, ou compte non validé.");
            return "redirect:/membre/login-form";
        }
    }

    @GetMapping("/espace")
    public String espaceMembre(HttpSession session, Model model) {
        Map<String, Object> membre = (Map<String, Object>) session.getAttribute("membreConnecte");
        if (membre == null) {
            return "redirect:/membre/login-form";
        }
        model.addAttribute("membre", membre);
        return "espaceMembre";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/membre/login-form";
    }
}