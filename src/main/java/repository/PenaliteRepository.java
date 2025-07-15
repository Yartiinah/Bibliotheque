package repository;

import model.Adherent;
import model.Penalite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {
    boolean existsByAdherentAndDateFinAfter(Adherent adherent, LocalDateTime now);
    Penalite findFirstByAdherentAndDateFinAfterOrderByDateFinDesc(Adherent adherent, LocalDateTime now);
}