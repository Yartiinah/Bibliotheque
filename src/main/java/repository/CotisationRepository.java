package com.example.library.repository;

import com.example.library.model.Cotisation;
import com.example.library.model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CotisationRepository extends JpaRepository<Cotisation, Integer> {
    List<Cotisation> findByMembre(Membre membre);
    Optional<Cotisation> findByMembreAndAnnee(Membre membre, Integer annee);
}