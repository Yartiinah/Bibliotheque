package com.example.library.repository;

import com.example.library.model.Reservation;
import com.example.library.model.Membre;
import com.example.library.model.Exemplaire;
import com.example.library.enums.ReservationStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByStatut(ReservationStatut statut);
    List<Reservation> findByMembre(Membre membre);
    Optional<Reservation> findByMembreAndExemplaireAndStatut(Membre membre, Exemplaire exemplaire, ReservationStatut statut);
    List<Reservation> findByExemplaireAndStatut(Exemplaire exemplaire, ReservationStatut statut);
}