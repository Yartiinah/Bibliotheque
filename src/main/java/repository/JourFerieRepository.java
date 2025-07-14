package repository;

import model.JourFerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourFerieRepository extends JpaRepository<JourFerie, Integer> {
}