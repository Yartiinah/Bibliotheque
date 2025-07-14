package com.example.library.repository;

import com.example.library.model.Adhesion;
import com.example.library.model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AdhesionRepository extends JpaRepository<Adhesion, Integer> {
    Optional<Adhesion> findByMembreAndDateExpirationAfter(Membre membre, LocalDate date);
    // Pour trouver la dernière adhésion valide d'un membre
    Optional<Adhesion> findTopByMembreOrderByDateExpirationDesc(Membre membre);
}