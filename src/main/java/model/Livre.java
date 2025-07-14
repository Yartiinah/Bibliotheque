package com.example.library.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 150)
    private String titre;

    @Column(length = 100)
    private String auteur;

    @ManyToOne(fetch = FetchType.LAZY) // Plusieurs livres peuvent appartenir à une catégorie
    @JoinColumn(name = "categorie_id", nullable = false) // Colonne de jointure dans la table livre
    private Categorie categorie;

    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Exemplaire> exemplaires;

    // Constructeurs
    public Livre() {
    }

    public Livre(String titre, String auteur, Categorie categorie) {
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Set<Exemplaire> getExemplaires() {
        return exemplaires;
    }

    public void setExemplaires(Set<Exemplaire> exemplaires) {
        this.exemplaires = exemplaires;
    }

    @Override
    public String toString() {
        return "Livre{" +
               "id=" + id +
               ", titre='" + titre + '\'' +
               ", auteur='" + auteur + '\'' +
               '}';
    }
}