package com.example.library.model;

import com.example.library.enums.ExemplaireStatut;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "exemplaire")
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    @Column(name = "code_exemplaire", unique = true, length = 50)
    private String codeExemplaire;

    @Enumerated(EnumType.STRING) // Mapper l'enum Java à la chaîne de caractères de la BDD
    @Column(columnDefinition = "exemplaire_statut default 'DISPONIBLE'") // Utilisation du type PostgreSQL
    private ExemplaireStatut statut;

    @Column(length = 100)
    private String localisation;

    @OneToMany(mappedBy = "exemplaire", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Pret> prets;

    @OneToMany(mappedBy = "exemplaire", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reservation> reservations;

    // Constructeurs
    public Exemplaire() {
    }

    public Exemplaire(Livre livre, String codeExemplaire, ExemplaireStatut statut, String localisation) {
        this.livre = livre;
        this.codeExemplaire = codeExemplaire;
        this.statut = statut;
        this.localisation = localisation;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public String getCodeExemplaire() {
        return codeExemplaire;
    }

    public void setCodeExemplaire(String codeExemplaire) {
        this.codeExemplaire = codeExemplaire;
    }

    public ExemplaireStatut getStatut() {
        return statut;
    }

    public void setStatut(ExemplaireStatut statut) {
        this.statut = statut;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
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
        return "Exemplaire{" +
               "id=" + id +
               ", codeExemplaire='" + codeExemplaire + '\'' +
               ", statut=" + statut +
               ", localisation='" + localisation + '\'' +
               '}';
    }
}