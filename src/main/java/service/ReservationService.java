package service;

import model.Exemplaire;
import model.Membre;
import model.Reservation;
import model.StatutExemplaire;
import model.StatutReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ExemplaireRepository;
import repository.MembreRepository;
import repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Transactional
    public boolean reserver(int membreId, int exemplaireId) {
        Membre membre = membreRepository.findById(membreId).orElse(null);
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId).orElse(null);
        if (membre == null || exemplaire == null || exemplaire.getStatut() != StatutExemplaire.DISPONIBLE) {
            return false;
        }
        Reservation reservation = new Reservation();
        reservation.setMembre(membre);
        reservation.setExemplaire(exemplaire);
        reservation.setDateReservation(LocalDateTime.now());
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        reservationRepository.save(reservation);
        return true;
    }

    public List<Reservation> getReservationsEnAttente() {
        return reservationRepository.findByStatut(StatutReservation.EN_ATTENTE);
    }

    @Transactional
    public void accepterReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée"));
        reservation.setStatut(StatutReservation.ACCEPTEE);
        reservation.getExemplaire().setStatut(StatutExemplaire.RESERVE);
        reservationRepository.save(reservation);
        exemplaireRepository.save(reservation.getExemplaire());
    }

    @Transactional
    public void refuserReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Réservation non trouvée"));
        reservation.setStatut(StatutReservation.REFUSEE);
        reservationRepository.save(reservation);
    }
}