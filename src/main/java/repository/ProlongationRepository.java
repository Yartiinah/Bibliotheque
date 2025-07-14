package repository;

import model.Prolongation;
import model.StatutProlongation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProlongationRepository extends JpaRepository<Prolongation, Integer> {
    List<Prolongation> findByStatut(StatutProlongation statut);
    Optional<Prolongation> findByPretIdAndStatut(int pretId, StatutProlongation statut);
}