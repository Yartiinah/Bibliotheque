
package repository;

import model.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface PretRepository extends JpaRepository<Pret, Integer> {
    Pret findByExemplaireReferenceAndStatut(String reference, String statut);

    @Query("SELECT p FROM Pret p WHERE p.exemplaire.reference = :reference AND DATE(p.dateEmprunt) = DATE(:dateEmprunt)")
    Pret findByExemplaireReferenceAndDateEmprunt(@Param("reference") String reference, @Param("dateEmprunt") LocalDateTime dateEmprunt);

    List<Pret> findByAdherentId(Integer adherentId);

    List<Pret> findByExemplaireId(Integer exemplaireId);
}
