package controller;

import model.Reservation;
import model.Adherent;
import model.Livre;
import model.Exemplaire;
import service.ReservationService;
import service.AdherentService;
import service.PretService;
import repository.AdherentRepository;
import repository.ReservationRepository;
import repository.ExemplaireRepository;
import repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    private ReservationRepository reservationRepository;
    @Autowired
    private AdherentService adherentService;
        @Autowired
    private AdherentRepository adherentRepository;
    @Autowired
    private ExemplaireRepository exemplaireRepository;
    
    @Autowired
    private LivreRepository livreRepository;
    @Autowired
    private PretService pretService;

    @GetMapping("/reservation/choisir-exemplaire")
    public String choisirExemplaire(@RequestParam("livreId") Integer livreId, Model model){
        List<Exemplaire> exemplaires = exemplaireRepository.findByLivre(livreRepository.findById(livreId).orElse(null));
        model.addAttribute("livre", livreRepository.findById(livreId).orElse(null));
        model.addAttribute("exemplaires", exemplaires);
        return "choisirExemplaire";
    }

    @PostMapping("/reservation/demander")
    public String demanderReservation(@RequestParam("exemplaireId") Integer exemplaireId,@RequestParam("dateDemande") String dateDemande,Model model,HttpSession session){
        model.Utilisateur user = (model.Utilisateur) session.getAttribute("user");
        Integer adherentId = user.getAdherent().getId();

        Adherent adherent = adherentRepository.findById(adherentId).orElse(null);
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId).orElse(null);
        Livre livre = exemplaire.getLivre();
        LocalDateTime dateDemandeParsed = LocalDate.parse(dateDemande).atStartOfDay();
        // Restriction d'âge
        if(livre.getRestriction().equals("adulte")){
            if(adherent.getTypeAbonnement().getLibelle().equals("enfant")){
                model.addAttribute("livre", livre);
                model.addAttribute("exemplaires", exemplaireRepository.findByLivre(livre));
                model.addAttribute("erreur", "Ce livre est réservé aux plus de 18 ans.");
                return "choisirExemplaire";
            }
        }
        // Vérification du quota de réservation
        int quota = adherent.getTypeAbonnement().getQuotaReservation();
        long nbReservationsEnCours = reservationService.getReservationsByAdherent(adherentId).stream()
            .filter(r -> "en_attente".equals(r.getStatut()) || "acceptee".equals(r.getStatut()))
            .count();
        if (nbReservationsEnCours >= quota) {
            String message = "Vous avez atteint votre quota de réservations (" + nbReservationsEnCours + "/" + quota + ").";
            model.addAttribute("livre", livre);
            model.addAttribute("exemplaires", exemplaireRepository.findByLivre(livre));
            model.addAttribute("erreur", message);
            return "choisirExemplaire";
        }
        // Vérification disponibilité exemplaire à la date choisie
        /* if (reservationService.isExemplaireReserveOuEmprunteA(exemplaireId, dateDemandeParsed)) {
            model.addAttribute("livre", livre);
            model.addAttribute("exemplaires", exemplaireRepository.findByLivre(livre));
            model.addAttribute("erreur", "Cet exemplaire n'est pas disponible à la date choisie.");
            return "choisirExemplaire";
        } */
        Reservation reservation = new Reservation();
        reservation.setAdherent(adherent);
        reservation.setExemplaire(exemplaire);
        reservation.setDateDemande(dateDemandeParsed);
        reservation.setStatut("en_attente"); // Statut initial
        reservationService.save(reservation);
        model.addAttribute("message", "Réservation demandée avec succès !");
        return "redirect:mesReservations";
    }

    // Bibliothécaire : liste des réservations à valider
    @GetMapping("/reservation/attente")
    public String reservationsEnAttente(Model model) {
        List<Reservation> reservations = reservationService.getReservationsEnAttente();
        model.addAttribute("reservations", reservations);
        return "reservationsAttente";
    }

    // Bibliothécaire : détail d'une réservation
    @GetMapping("/reservation/detail")
    public String detailReservation(@RequestParam Integer id, Model model) {
        Optional<Reservation> opt = reservationService.getById(id);
        if (opt.isPresent()) {
            Reservation reservation = opt.get();
            model.addAttribute("reservation", reservation);
            // Ajoute les infos de l'adhérent, ses prêts, etc.
            model.addAttribute("adherent", reservation.getAdherent());
            model.addAttribute("prets", pretService.getPretsByAdherent(reservation.getAdherent().getId()));
            return "reservationDetail";
        }
        model.addAttribute("message", "Réservation introuvable");
        return "redirect:/reservation/attente";
    }

    @GetMapping("/reservation/mesReservations")
    public String mesReservations(Model model, HttpSession session) {
        model.Utilisateur user = (model.Utilisateur) session.getAttribute("user");
        Integer adherentId = user.getAdherent().getId();
        List<Reservation> reservations = reservationService.getReservationsByAdherent(adherentId);
        model.addAttribute("reservations", reservations);
        return "mesReservations";
    }

    // Bibliothécaire : accepter/refuser
    @PostMapping("/reservation/valider")
    public String validerReservation(@RequestParam("id") Integer id, @RequestParam("action") String action, Model model, RedirectAttributes redirectAttributes) {
        Optional<Reservation> opt = reservationService.getById(id);
        if (opt.isPresent()) {
            Reservation reservation = opt.get();
            if ("accepter".equals(action)) {
                reservation.setStatut("acceptee");
                reservationService.save(reservation);
                // Redirige vers le formulaire d'ajout de prêt, en passant les infos nécessaires
                return "redirect:/prets/ajouter?adherentId=" + reservation.getAdherent().getId()
                        + "&referenceExemplaire=" + reservation.getExemplaire().getReference()
                        + "&dateEmprunt=" + reservation.getDateDemande().toLocalDate()
                        + "&reservationId=" + reservation.getId();
            } else {
                reservation.setStatut("refusee");
                reservationService.save(reservation);
            }
        }
        return "redirect:/reservation/attente";
    }
}
