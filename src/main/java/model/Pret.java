package com.example.library.model;

import com.example.library.enums.PretType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "pret")
public class Pret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre membre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exemplaire_id", nullable = false)
    private Exemplaire exemplaire;

    @Column(name = "date_emprunt")
    private LocalDate dateEmprunt;

    @Column(name = "date_retour_prevue")
    private LocalDate dateRetourPrevue;

    @Column(name = "date_retour_effective")
    private LocalDate dateRetourEffective;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_pret", columnDefinition = "pret_type")
    private PretType typePret;

    @OneToMany(mappedBy = "pret", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Prolongation> prolongations;

    // Constructeurs
    public Pret() {
    }

    public Pret(Membre membre, Exemplaire exemplaire, LocalDate dateEmprunt, LocalDate dateRetourPrevue, PretType typePret) {
        this.membre = membre;
        this.exemplaire = exemplaire;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.typePret = typePret;
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

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(LocalDate dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    public PretType getTypePret() {
        return typePret;
    }

    public void setTypePret(PretType typePret) {
        this.typePret = typePret;
    }

    public Set<Prolongation> getProlongations() {
        return prolongations;
    }

    public void setProlongations(Set<Prolongation> prolongations) {
        this.prolongations = prolongations;
    }

    @Override
    public String toString() {
        return "Pret{" +
               "id=" + id +
               ", dateEmprunt=" + dateEmprunt +
               ", dateRetourPrevue=" + dateRetourPrevue +
               ", typePret=" + typePret +
               '}';
    }
}