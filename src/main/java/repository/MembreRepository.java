package repository;

import model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Integer> {

    @Query("SELECT m FROM Membre m JOIN m.inscriptions i WHERE i.statut = :statut")
    List<Membre> findByStatutValidation(String statut);

    @Query("SELECT m FROM Membre m WHERE m.nom LIKE %:motCle% OR m.email LIKE %:motCle%")
    List<Membre> rechercheMembre(String motCle);

    Membre findByEmailAndNomAndStatutValidation(String email, String nom, String statutValidation);

    @Query("SELECT m FROM Membre m JOIN m.inscriptions i WHERE i.statut = 'VALIDE'")
    List<Membre> findByStatutValidation();
}