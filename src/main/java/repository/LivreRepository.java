package com.example.library.repository;

import com.example.library.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {
    List<Livre> findByTitreContainingIgnoreCase(String titre);
    List<Livre> findByAuteurContainingIgnoreCase(String auteur);
    List<Livre> findByTitreContainingIgnoreCaseOrAuteurContainingIgnoreCase(String titre, String auteur);
}