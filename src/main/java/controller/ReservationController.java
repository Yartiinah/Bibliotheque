package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import service.EmpruntService;
import service.ReservationService;

import java.util.Map;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private EmpruntService empruntService;

    @Autowired
    private ReservationService reservationService;

    // Vérifie si l'utilisateur est un bibliothécaire
    private boolean isBibliothecaire(HttpSession session, RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("BIBLIOTHECAIRE")) {
            redirectAttributes.addFlashAttribute("erreur", "Accès non autorisé.");
            return false;
        }
        return true;
    }

    // Affiche le formulaire de réservation (reservationForm.jsp)
    @GetMapping("/form")
    public String reservationForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Map<String, Object> membre = (Map<String, Object>) session.getAttribute("membreConnecte");
        if (membre == null) {
            redirectAttributes.addFlashAttribute("erreur", "Veuillez vous connecter.");
            return "redirect:/membre/login-form";
        }
        model.addAttribute("exemplaires", empruntService.getExemplairesDisponibles());
        return "reservationForm"; // Compatible avec reservationForm.jsp
    }

    // Effectue une réservation
    @PostMapping("/reserver")
    public String reserver(
            @RequestParam int exemplaireId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Map<String, Object> membre = (Map<String, Object>) session.getAttribute("membreConnecte");
        if (membre == null) {
            redirectAttributes.addFlashAttribute("erreur", "Veuillez vous connecter.");
            return "redirect:/membre/login-form";
        }
        try {
            boolean success = reservationService.reserver((Integer) membre.get("id"), exemplaireId);
            redirectAttributes.addFlashAttribute("message", success ? "Réservation effectuée avec succès." : "Erreur lors de la réservation.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur : exemplaire non disponible.");
        }
        return "redirect:/reservation/form";
    }

    // Affiche la gestion des réservations (gestionReservations.jsp)
    @GetMapping("/gestion")
    public String gestionReservations(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        model.addAttribute("reservations", reservationService.getReservationsEnAttente());
        return "gestionReservations"; // Compatible avec gestionReservations.jsp
    }

    // Accepte une réservation
    @PostMapping("/accepter")
    public String accepterReservation(
            @RequestParam int id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            reservationService.accepterReservation(id);
            redirectAttributes.addFlashAttribute("message", "Réservation acceptée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de l'acceptation de la réservation.");
        }
        return "redirect:/reservation/gestion";
    }

    // Refuse une réservation
    @PostMapping("/refuser")
    public String refuserReservation(
            @RequestParam int id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session, redirectAttributes)) {
            return "redirect:/login";
        }
        try {
            reservationService.refuserReservation(id);
            redirectAttributes.addFlashAttribute("message", "Réservation refusée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors du refus de la réservation.");
        }
        return "redirect:/reservation/gestion";
    }
}