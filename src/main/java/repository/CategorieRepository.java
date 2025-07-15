package com.example.library.repository;

import com.example.library.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
    // Aucune méthode spécifique n'est ajoutée pour l'instant au-delà des CRUD de base
}