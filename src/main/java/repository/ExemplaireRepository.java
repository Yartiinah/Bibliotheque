package repository;

import model.Exemplaire;
import model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    Optional<Exemplaire> findByReference(String reference);
    List<Exemplaire> findByLivre(Livre livre);
}