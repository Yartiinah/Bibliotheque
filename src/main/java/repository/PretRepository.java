package repository;

import model.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
    @Query("SELECT p FROM Pret p JOIN p.penalites pen WHERE p.membre.id = :membreId AND pen.dateFin > :date")
    List<Pret> findByMembreIdAndDateFinPenaliteAfter(int membreId, LocalDateTime date);

    List<Pret> findByMembreIdAndDateRetourEffectiveIsNull(int membreId);

    List<Pret> findByDateRetourEffectiveIsNull();
}