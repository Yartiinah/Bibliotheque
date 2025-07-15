package com.example.library.repository;

import com.example.library.model.Penalite;
import com.example.library.model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {
    List<Penalite> findByMembre(Membre membre);
    List<Penalite> findByMembreAndDateFinPenaliteAfter(Membre membre, LocalDate date); // Pénalités actives
}