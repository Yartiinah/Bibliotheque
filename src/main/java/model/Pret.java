package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Pret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_exemplaire")
    private Exemplaire exemplaire;

    @ManyToOne
    @JoinColumn(name = "id_adherent")
    private Adherent adherent;

    @Column(name = "date_emprunt")
    private LocalDateTime dateEmprunt;

    @Column(name = "date_retour_prevue")
    private LocalDateTime dateRetourPrevue;

    @Column(name = "date_retour_effective")
    private LocalDateTime dateRetourEffective;

    @Column(name="type_pret",nullable = false)
    private String typePret; // "sur_place" ou "emporte"

    @Column(nullable = false)
    private String statut; // "en_cours", "termine", "en_retard"

    // Getters et setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public LocalDateTime getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDateTime dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDateTime getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDateTime dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public LocalDateTime getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(LocalDateTime dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    public String getTypePret() {
        return typePret;
    }

    public void setTypePret(String typePret) {
        this.typePret = typePret;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    private int nbProlongements = 0;

public int getNbProlongements() { return nbProlongements; }
public void setNbProlongements(int nbProlongements) { this.nbProlongements = nbProlongements; }

    @ManyToOne
    @JoinColumn(name = "id_pret_origine")
    private Pret pretOrigine; // référence au prêt d'origine si prolongement

    // Getters et setters...
    public Pret getPretOrigine() { return pretOrigine; }
    public void setPretOrigine(Pret pretOrigine) { this.pretOrigine = pretOrigine; }
}