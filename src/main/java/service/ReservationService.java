package service;

import model.Reservation;
import repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import repository.PretRepository;
import model.Pret;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PretRepository pretRepository;

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsEnAttente() {
        return reservationRepository.findByStatut("en_attente");
    }

    public List<Reservation> getReservationsByAdherent(Integer adherentId) {
        return reservationRepository.findByAdherentId(adherentId);
    }

    public Optional<Reservation> getById(Integer id) {
        return reservationRepository.findById(id);
    }

    public void delete(Integer id) {
        reservationRepository.deleteById(id);
    }

    /**
     * Vérifie si un exemplaire est réservé ou emprunté à une date donnée, en ignorant éventuellement une réservation (par id).
     */
    public boolean isExemplaireReserveOuEmprunteA(Integer exemplaireId, java.time.LocalDateTime date, Integer reservationIdToIgnore) {
        // 1. Récupère toutes les réservations pour cet exemplaire
        List<Reservation> reservations = reservationRepository.findByExemplaireId(exemplaireId);
        for (Reservation r : reservations) {
            // Ignore la réservation à ignorer (utile lors de la validation)
            if (reservationIdToIgnore != null && r.getId().equals(reservationIdToIgnore)) continue;
            // 2. Si la réservation est "en_attente" ou "acceptee"
            //    ET que la date de demande correspond à la date recherchée
            if (("en_attente".equals(r.getStatut()) || "acceptee".equals(r.getStatut()))
                && r.getDateDemande().toLocalDate().isEqual(date.toLocalDate())) {
                return true; // L'exemplaire est déjà réservé à cette date
            }
        }
        // 3. Récupère tous les prêts pour cet exemplaire
        List<Pret> prets = pretRepository.findByExemplaireId(exemplaireId);
        for (Pret p : prets) {
            // 4. Si le prêt est "en_cours"
            //    ET que la date recherchée est comprise entre la date d'emprunt et la date de retour prévue
            if ("en_cours".equals(p.getStatut())
                && (date.isAfter(p.getDateEmprunt()) && date.isBefore(p.getDateRetourPrevue()))) {
                return true; // L'exemplaire est déjà emprunté à cette date
            }
        }
        // 5. Si aucune réservation ni prêt ne bloque la date, l'exemplaire est disponible
        return false;
    }

    // Surcharge pour compatibilité avec l'ancien appel (sans id à ignorer)
    public boolean isExemplaireReserveOuEmprunteA(Integer exemplaireId, java.time.LocalDateTime date) {
        return isExemplaireReserveOuEmprunteA(exemplaireId, date, null);
    }
}
