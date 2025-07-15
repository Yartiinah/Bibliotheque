package repository;

import model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivreRepository extends JpaRepository<Livre, Integer> {
    List<Livre> findByCategorieId(int categorieId);
    Livre findByIsbn(String isbn);
}
