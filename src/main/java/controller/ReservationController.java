package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import service.ReservationService;
import service.EmpruntService;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private EmpruntService empruntService;

    private boolean isMembre(HttpSession session) {
        Map<String, Object> membre = (Map<String, Object>) session.getAttribute("membreConnecte");
        return membre != null;
    }

    private boolean isBibliothecaire(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "BIBLIOTHECAIRE".equals(role);
    }

    @GetMapping("/form")
    public String afficherFormulaireReservation(HttpSession session, Model model) {
        if (!isMembre(session)) {
            return "redirect:/membre/login-form";
        }
        model.addAttribute("exemplaires", empruntService.getExemplairesDisponibles());
        return "reservationForm";
    }

    @PostMapping("/reserver")
    public String reserver(
            @RequestParam int exemplaireId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isMembre(session)) {
            return "redirect:/membre/login-form";
        }
        Map<String, Object> membre = (Map<String, Object>) session.getAttribute("membreConnecte");
        int membreId = (Integer) membre.get("id");
        boolean success = reservationService.reserver(membreId, exemplaireId);
        redirectAttributes.addFlashAttribute("message", success ? "Réservation effectuée." : "Erreur lors de la réservation.");
        return "redirect:/reservation/form";
    }

    @GetMapping("/gestion")
    public String gestionReservations(HttpSession session, Model model) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        model.addAttribute("reservations", reservationService.getReservationsEnAttente());
        return "gestionReservations";
    }

    @PostMapping("/accepter")
    public String accepterReservation(@RequestParam int id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        boolean success = reservationService.accepterReservation(id);
        redirectAttributes.addFlashAttribute("message", success ? "Réservation acceptée." : "Erreur lors de l'acceptation.");
        return "redirect:/reservation/gestion";
    }

    @PostMapping("/refuser")
    public String refuserReservation(@RequestParam int id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isBibliothecaire(session)) {
            return "redirect:/login";
        }
        boolean success = reservationService.refuserReservation(id);
        redirectAttributes.addFlashAttribute("message", success ? "Réservation refusée." : "Erreur lors du refus.");
        return "redirect:/reservation/gestion";
    }
}