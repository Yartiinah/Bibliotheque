package com.example.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "penalite")
public class Penalite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre membre;

    @Column(name = "date_debut_penalite")
    private LocalDate dateDebutPenalite;

    @Column(name = "date_fin_penalite")
    private LocalDate dateFinPenalite;

    @Column(columnDefinition = "TEXT")
    private String motif;

    // Constructeurs
    public Penalite() {
    }

    public Penalite(Membre membre, LocalDate dateDebutPenalite, LocalDate dateFinPenalite, String motif) {
        this.membre = membre;
        this.dateDebutPenalite = dateDebutPenalite;
        this.dateFinPenalite = dateFinPenalite;
        this.motif = motif;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public LocalDate getDateDebutPenalite() {
        return dateDebutPenalite;
    }

    public void setDateDebutPenalite(LocalDate dateDebutPenalite) {
        this.dateDebutPenalite = dateDebutPenalite;
    }

    public LocalDate getDateFinPenalite() {
        return dateFinPenalite;
    }

    public void setDateFinPenalite(LocalDate dateFinPenalite) {
        this.dateFinPenalite = dateFinPenalite;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    @Override
    public String toString() {
        return "Penalite{" +
               "id=" + id +
               ", membreId=" + (membre != null ? membre.getId() : "null") +
               ", dateDebutPenalite=" + dateDebutPenalite +
               ", dateFinPenalite=" + dateFinPenalite +
               ", motif='" + motif + '\'' +
               '}';
    }
}