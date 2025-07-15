package repository;

import model.JourFerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Optional;

public interface JourFerieRepository extends JpaRepository<JourFerie, Integer> {
    Optional<JourFerie> findByDateFerie(LocalDate dateFerie);

    @Query("SELECT CASE WHEN COUNT(j) > 0 THEN true ELSE false END FROM JourFerie j WHERE j.dateFerie = :date")
    boolean existsByDateFerie(@Param("date") LocalDate date);
}
