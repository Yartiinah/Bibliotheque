package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pret")
public class Pret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_exemplaire", nullable = false)
    private Exemplaire exemplaire;

    @ManyToOne
    @JoinColumn(name = "id_adherent", nullable = false)
    private Membre membre;

    @Column(name = "date_emprunt", nullable = false)
    private LocalDateTime dateEmprunt;

    @Column(name = "date_retour_prevue", nullable = false)
    private LocalDateTime dateRetourPrevue;

    @Column(name = "date_retour_effective")
    private LocalDateTime dateRetourEffective;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_pret", nullable = false)
    private TypePret typePret;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPret statut;

    @Column(name = "nbprolongements", nullable = false)
    private Integer nbProlongements = 0;

    @ManyToOne
    @JoinColumn(name = "id_pret_origine")
    private Pret pretOrigine;

    // Getters and Setters
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

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
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

    public TypePret getTypePret() {
        return typePret;
    }

    public void setTypePret(TypePret typePret) {
        this.typePret = typePret;
    }

    public StatutPret getStatut() {
        return statut;
    }

    public void setStatut(StatutPret statut) {
        this.statut = statut;
    }

    public Integer getNbProlongements() {
        return nbProlongements;
    }

    public void setNbProlongements(Integer nbProlongements) {
        this.nbProlongements = nbProlongements;
    }

    public Pret getPretOrigine() {
        return pretOrigine;
    }

    public void setPretOrigine(Pret pretOrigine) {
        this.pretOrigine = pretOrigine;
    }
}

enum TypePret {
    SUR_PLACE, EMPORTE
}

enum StatutPret {
    EN_COURS, TERMINE, EN_RETARD
}