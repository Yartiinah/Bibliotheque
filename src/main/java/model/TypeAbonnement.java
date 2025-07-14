package model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "type_abonnement")
public class TypeAbonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private LibelleAbonnement libelle;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal tarif;

    @Column(name = "quota_livre", nullable = false)
    private Integer quotaLivre;

    @Column(name = "duree_pret_jour", nullable = false)
    private Integer dureePretJour = 14;

    @Column(name = "quota_reservation", nullable = false)
    private Integer quotaReservation = 2;

    @Column(name = "quota_prolongement", nullable = false)
    private Integer quotaProlongement = 1;

    @Column(name = "nb_jour_prolongement", nullable = false)
    private Integer nbJourProlongement = 7;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LibelleAbonnement getLibelle() {
        return libelle;
    }

    public void setLibelle(LibelleAbonnement libelle) {
        this.libelle = libelle;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    public void setTarif(BigDecimal tarif) {
        this.tarif = tarif;
    }

    public Integer getQuotaLivre() {
        return quotaLivre;
    }

    public void setQuotaLivre(Integer quotaLivre) {
        this.quotaLivre = quotaLivre;
    }

    public Integer getDureePretJour() {
        return dureePretJour;
    }

    public void setDureePretJour(Integer dureePretJour) {
        this.dureePretJour = dureePretJour;
    }

    public Integer getQuotaReservation() {
        return quotaReservation;
    }

    public void setQuotaReservation(Integer quotaReservation) {
        this.quotaReservation = quotaReservation;
    }

    public Integer getQuotaProlongement() {
        return quotaProlongement;
    }

    public void setQuotaProlongement(Integer quotaProlongement) {
        this.quotaProlongement = quotaProlongement;
    }

    public Integer getNbJourProlongement() {
        return nbJourProlongement;
    }

    public void setNbJourProlongement(Integer nbJourProlongement) {
        this.nbJourProlongement = nbJourProlongement;
    }
}

enum LibelleAbonnement {
    ENFANT, ETUDIANT, ADULTE, SENIOR, PROFESSIONNEL, PROFESSEUR
}