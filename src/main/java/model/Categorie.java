package com.example.library.model; // Ou votre package d'entités

import jakarta.persistence.*;
import java.util.Set; // Pour la relation OneToMany avec Livre

@Entity
@Table(name = "categorie")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String nom; // Enfant, +18, Tous publics

    private Integer ageMinimum; // age_minimum dans la base de données

    // Relation OneToMany avec Livre (une catégorie peut avoir plusieurs livres)
    // MappedBy indique le champ dans l'entité Livre qui possède la relation (categorie)
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Livre> livres; // Utiliser Set pour éviter les doublons et pour de meilleures performances avec les collections

    // Constructeurs
    public Categorie() {
    }

    public Categorie(String nom, Integer ageMinimum) {
        this.nom = nom;
        this.ageMinimum = ageMinimum;
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

    public Integer getAgeMinimum() {
        return ageMinimum;
    }

    public void setAgeMinimum(Integer ageMinimum) {
        this.ageMinimum = ageMinimum;
    }

    public Set<Livre> getLivres() {
        return livres;
    }

    public void setLivres(Set<Livre> livres) {
        this.livres = livres;
    }

    @Override
    public String toString() {
        return "Categorie{" +
               "id=" + id +
               ", nom='" + nom + '\'' +
               ", ageMinimum=" + ageMinimum +
               '}';
    }
}