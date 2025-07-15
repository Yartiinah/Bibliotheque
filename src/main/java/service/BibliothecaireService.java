package service;

import model.Categorie;
import model.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.CategorieRepository;
import repository.LivreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BibliothecaireService {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private LivreRepository livreRepository;

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    @Transactional
    public void ajouterCategorie(String nom) {
        Categorie categorie = new Categorie();
        categorie.setNom(nom);
        categorieRepository.save(categorie);
    }

    @Transactional
    public void modifierCategorie(int id, String nom) {
        Categorie categorie = categorieRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));
        categorie.setNom(nom);
        categorieRepository.save(categorie);
    }

    @Transactional
    public void supprimerCategorie(int id) {
        categorieRepository.deleteById(id);
    }

    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    @Transactional
    public void ajouterLivre(String titre, String auteur, int categorieId) {
        Categorie categorie = categorieRepository.findById(categorieId).orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));
        Livre livre = new Livre();
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setCategorie(categorie);
        livreRepository.save(livre);
    }

    @Transactional
    public void modifierLivre(int id, String titre, String auteur, int categorieId) {
        Livre livre = livreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Livre non trouvé"));
        Categorie categorie = categorieRepository.findById(categorieId).orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setCategorie(categorie);
        livreRepository.save(livre);
    }

    @Transactional
    public void supprimerLivre(int id) {
        livreRepository.deleteById(id);
    }
}