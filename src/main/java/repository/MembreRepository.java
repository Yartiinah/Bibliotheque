package com.example.library.repository;

import com.example.library.model.Membre;
import com.example.library.enums.MembreStatutValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Integer> {
    List<Membre> findByStatutValidation(MembreStatutValidation statutValidation);
    Optional<Membre> findByEmail(String email);
    List<Membre> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCaseOrEmailContainingIgnoreCase(String nom, String prenom, String email);
}