package com.example.library.model;

import com.example.library.enums.ProlongationStatut;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prolongation")
public class Prolongation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pret_id", nullable = false)
    private Pret pret;

    @Column(name = "date_demande")
    private LocalDate dateDemande;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "prolongation_statut default 'EN_ATTENTE'")
    private ProlongationStatut statut;

    // Constructeurs
    public Prolongation() {
    }

    public Prolongation(Pret pret, LocalDate dateDemande, ProlongationStatut statut) {
        this.pret = pret;
        this.dateDemande = dateDemande;
        this.statut = statut;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public ProlongationStatut getStatut() {
        return statut;
    }

    public void setStatut(ProlongationStatut statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Prolongation{" +
               "id=" + id +
               ", pretId=" + (pret != null ? pret.getId() : "null") +
               ", dateDemande=" + dateDemande +
               ", statut=" + statut +
               '}';
    }
}