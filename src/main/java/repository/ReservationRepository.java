package repository;

import model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByStatut(String statut);
    List<Reservation> findByAdherentId(Integer adherentId);
    List<Reservation> findByExemplaireId(Integer exemplaireId);
}
