package repository;

import model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription, Integer> {
    List<Inscription> findByAdherentId(int adherentId);
}
