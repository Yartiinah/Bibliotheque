package repository;

import model.Pret;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PretRepository extends JpaRepository<Pret, Integer> {
    Pret findByExemplaireReferenceAndStatut(String reference, String statut);
    List<Pret> findByAdherentId(Integer adherentId);
    List<Pret> findByExemplaireId(Integer exemplaireId);
}