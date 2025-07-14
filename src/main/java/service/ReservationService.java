package service;

import model.Reservation;
import model.Membre;
import model.Exemplaire;
import repository.ReservationRepository;
import repository.MembreRepository;
import repository.ExemplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    public boolean peutReserver(int membreId) {
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new IllegalArgumentException("Membre non trouvé"));
        int quota = membre.getTypeAbonnement().getQuotaReservation();
        long nbReservations = reservationRepository.countByMembreIdAndStatut(membreId, StatutReservation.EN_ATTENTE);
        return nbReservations < quota && !new EmpruntService().aPenaliteEnCours(membreId);
    }

    @Transactional
    public boolean reserver(int membreId, int exemplaireId) {
        if (!peutReserver(membreId)) {
            return false;
        }
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new IllegalArgumentException("Membre non trouvé"));
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId)
                .orElseThrow(() -> new IllegalArgumentException("Exemplaire non trouvé"));
        Reservation reservation = new Reservation();
        reservation.setMembre(membre);
        reservation.setExemplaire(exemplaire);
        reservation.setDateDemande(LocalDateTime.now());
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        reservationRepository.save(reservation);
        return true;
    }

    public List<Map<String, Object>> getReservationsEnAttente() {
        return reservationRepository.findByStatut(StatutReservation.EN_ATTENTE).stream()
                .map(r -> Map.of(
                        "id", r.getId(),
                        "nom", r.getMembre().getNom(),
                        "prenom", r.getMembre().getPrenom(),
                        "titre", r.getExemplaire().getLivre().getTitre(),
                        "code_exemplaire", r.getExemplaire().getReference(),
                        "date_reservation", r.getDateDemande()))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean accepterReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée"));
        reservation.setStatut(StatutReservation.ACCEPTEE);
        reservation.getExemplaire().setStatut(StatutExemplaire.RESERVE);
        reservationRepository.save(reservation);
        exemplaireRepository.save(reservation.getExemplaire());
        return true;
    }

    @Transactional
    public boolean refuserReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée"));
        reservation.setStatut(StatutReservation.REFUSEE);
        reservationRepository.save(reservation);
        return true;
    }
}