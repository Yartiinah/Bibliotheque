package repository;

import model.TypeAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeAbonnementRepository extends JpaRepository<TypeAbonnement, Integer> {
    TypeAbonnement findByLibelle(String libelle);
}
