package repository;

import model.Reservation;
import model.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByStatut(StatutReservation statut);
    long countByMembreIdAndStatut(int membreId, StatutReservation statut);
}