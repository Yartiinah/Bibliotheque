package com.example.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "adhesion")
public class Adhesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre membre;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    // Constructeurs
    public Adhesion() {
    }

    public Adhesion(Membre membre, LocalDate dateDebut, LocalDate dateExpiration) {
        this.membre = membre;
        this.dateDebut = dateDebut;
        this.dateExpiration = dateExpiration;
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

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    @Override
    public String toString() {
        return "Adhesion{" +
               "id=" + id +
               ", membreId=" + (membre != null ? membre.getId() : "null") +
               ", dateDebut=" + dateDebut +
               ", dateExpiration=" + dateExpiration +
               '}';
    }
}