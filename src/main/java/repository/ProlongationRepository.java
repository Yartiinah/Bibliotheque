package com.example.library.repository;

import com.example.library.model.Prolongation;
import com.example.library.model.Pret;
import com.example.library.enums.ProlongationStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProlongationRepository extends JpaRepository<Prolongation, Integer> {
    List<Prolongation> findByStatut(ProlongationStatut statut);
    Optional<Prolongation> findByPretAndStatut(Pret pret, ProlongationStatut statut);
}