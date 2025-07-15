package repository;

import model.Inscription;
import model.StatutInscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription, Integer> {
    List<Inscription> findByStatut(StatutInscription statut);
    Inscription findByMembreIdAndStatut(int membreId, StatutInscription statut);
}