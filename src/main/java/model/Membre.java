package com.example.library.model;

import com.example.library.enums.MembreProfil;
import com.example.library.enums.MembreStatutValidation;
import com.example.library.enums.MembreTypeInscription;
import jakarta.persistence.*;
import java.time.LocalDate; // Nouvelle API Date/Time de Java
import java.util.Set;

@Entity
@Table(name = "membre")
public class Membre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String nom;

    @Column(length = 100)
    private String prenom;

    @Column(length = 100, unique = true)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String adresse;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance; // Utiliser LocalDate pour les dates sans heure

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "membre_profil")
    private MembreProfil profil;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_inscription", columnDefinition = "membre_type_inscription")
    private MembreTypeInscription typeInscription;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_validation", columnDefinition = "membre_statut_validation default 'EN_ATTENTE'")
    private MembreStatutValidation statutValidation;

    @OneToMany(mappedBy = "membre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Adhesion> adhesions;

    @OneToMany(mappedBy = "membre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Cotisation> cotisations;

    @OneToMany(mappedBy = "membre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Penalite> penalites;

    @OneToMany(mappedBy = "membre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Pret> prets;

    @OneToMany(mappedBy = "membre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reservation> reservations;

    // Constructeurs
    public Membre() {
    }

    public Membre(String nom, String prenom, String email, String adresse, LocalDate dateNaissance,
                  MembreProfil profil, MembreTypeInscription typeInscription, MembreStatutValidation statutValidation) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.profil = profil;
        this.typeInscription = typeInscription;
        this.statutValidation = statutValidation;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public MembreProfil getProfil() {
        return profil;
    }

    public void setProfil(MembreProfil profil) {
        this.profil = profil;
    }

    public MembreTypeInscription getTypeInscription() {
        return typeInscription;
    }

    public void setTypeInscription(MembreTypeInscription typeInscription) {
        this.typeInscription = typeInscription;
    }

    public MembreStatutValidation getStatutValidation() {
        return statutValidation;
    }

    public void setStatutValidation(MembreStatutValidation statutValidation) {
        this.statutValidation = statutValidation;
    }

    public Set<Adhesion> getAdhesions() {
        return adhesions;
    }

    public void setAdhesions(Set<Adhesion> adhesions) {
        this.adhesions = adhesions;
    }

    public Set<Cotisation> getCotisations() {
        return cotisations;
    }

    public void setCotisations(Set<Cotisation> cotisations) {
        this.cotisations = cotisations;
    }

    public Set<Penalite> getPenalites() {
        return penalites;
    }

    public void setPenalites(Set<Penalite> penalites) {
        this.penalites = penalites;
    }

    public Set<Pret> getPrets() {
        return prets;
    }

    public void setPrets(Set<Pret> prets) {
        this.prets = prets;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Membre{" +
               "id=" + id +
               ", nom='" + nom + '\'' +
               ", prenom='" + prenom + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}