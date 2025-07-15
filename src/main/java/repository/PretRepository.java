package com.example.library.repository;

import com.example.library.model.Pret;
import com.example.library.model.Membre;
import com.example.library.model.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
    List<Pret> findByMembre(Membre membre);
    Optional<Pret> findByMembreAndExemplaireAndDateRetourEffectiveIsNull(Membre membre, Exemplaire exemplaire);
    List<Pret> findByDateRetourEffectiveIsNull(); // Prêts en cours

    // Requête pour trouver les prêts en retard (date_retour_prevue < CURRENT_DATE et date_retour_effective IS NULL)
    @Query("SELECT p FROM Pret p WHERE p.dateRetourPrevue < CURRENT_DATE AND p.dateRetourEffective IS NULL")
    List<Pret> findPrêtsEnRetard();

    // Requête pour les prêts en cours avec infos complètes (similaire à votre getPretsEnCours DAO)
    // Note: Dans Spring Data JPA, vous obtiendrez des objets entiers, pas des Map<String, Object>
    @Query("SELECT p FROM Pret p JOIN FETCH p.membre m JOIN FETCH p.exemplaire e JOIN FETCH e.livre l WHERE p.dateRetourEffective IS NULL ORDER BY p.dateRetourPrevue")
    List<Pret> findAllPretsEnCoursWithDetails();
}