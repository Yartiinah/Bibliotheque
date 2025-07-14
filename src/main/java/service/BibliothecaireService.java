package service;

import model.Categorie;
import model.Livre;
import repository.CategorieRepository;
import repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BibliothecaireService {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private LivreRepository livreRepository;

    public List<Map<String, Object>> getAllCategories() {
        return categorieRepository.findAll().stream()
                .map(c -> Map.of(
                        "id", c.getId(),
                        "nom", c.getNom()))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean createCategorie(String nom) {
        Categorie categorie = new Categorie();
        categorie.setNom(nom);
        categorieRepository.save(categorie);
        return true;
    }

    @Transactional
    public boolean updateCategorie(int id, String nom) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));
        categorie.setNom(nom);
        categorieRepository.save(categorie);
        return true;
    }

    @Transactional
    public boolean deleteCategorie(int id) {
        categorieRepository.deleteById(id);
        return true;
    }

    public List<Map<String, Object>> getAllLivres() {
        return livreRepository.findAll().stream()
                .map(l -> Map.of(
                        "id", l.getId(),
                        "titre", l.getTitre(),
                        "auteur", l.getAuteur(),
                        "categorie_nom", l.getCategorie() != null ? l.getCategorie().getNom() : ""))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean createLivre(String titre, String auteur, int categorieId) {
        Categorie categorie = categorieRepository.findById(categorieId)
                .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));
        Livre livre = new Livre();
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setCategorie(categorie);
        livreRepository.save(livre);
        return true;
    }

    @Transactional
    public boolean updateLivre(int id, String titre, String auteur, int categorieId) {
        Categorie categorie = categorieRepository.findById(categorieId)
                .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));
        Livre livre = livreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livre non trouvé"));
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setCategorie(categorie);
        livreRepository.save(livre);
        return true;
    }

    @Transactional
    public boolean deleteLivre(int id) {
        livreRepository.deleteById(id);
        return true;
    }
}