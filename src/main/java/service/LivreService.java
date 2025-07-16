package service;

import model.Livre;
import model.Categorie;
import repository.LivreRepository;
import repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LivreService {
    @Autowired
    private LivreRepository livreRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    public Livre ajouterLivre(String titre, String auteur, Integer categorieId, String isbn, String restriction) {
        Categorie categorie = categorieRepository.findById(categorieId).orElse(null);
        if (categorie == null) return null;

        Livre livre = new Livre();
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setCategorie(categorie);
        livre.setIsbn(isbn);
        livre.setRestriction(restriction);
        
        return livreRepository.save(livre);
    }


    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Categorie ajouterCategorie(String nom) {
        Categorie cat = new Categorie();
        cat.setNom(nom);
        return categorieRepository.save(cat);
    }
}
