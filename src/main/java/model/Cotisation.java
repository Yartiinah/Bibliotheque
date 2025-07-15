package com.example.library.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cotisation")
public class Cotisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre membre;

    @Column(precision = 10, scale = 2)
    private BigDecimal montant;

    @Column(name = "date_paiement")
    private LocalDate datePaiement;

    private Integer annee;

    // Constructeurs
    public Cotisation() {
    }

    public Cotisation(Membre membre, BigDecimal montant, LocalDate datePaiement, Integer annee) {
        this.membre = membre;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.annee = annee;
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

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    @Override
    public String toString() {
        return "Cotisation{" +
               "id=" + id +
               ", membreId=" + (membre != null ? membre.getId() : "null") +
               ", montant=" + montant +
               ", datePaiement=" + datePaiement +
               ", annee=" + annee +
               '}';
    }
}