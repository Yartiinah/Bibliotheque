package com.example.library.repository;

import com.example.library.model.Exemplaire;
import com.example.library.enums.ExemplaireStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    Optional<Exemplaire> findByCodeExemplaire(String codeExemplaire);
    List<Exemplaire> findByLivreId(Integer livreId);
    List<Exemplaire> findByStatut(ExemplaireStatut statut);
    List<Exemplaire> findByLivreTitreContainingIgnoreCase(String titre); // Recherche par titre de livre
}