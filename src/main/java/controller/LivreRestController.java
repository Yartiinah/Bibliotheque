package controller;

import model.Exemplaire;
import model.Livre;
import repository.ExemplaireRepository;
import repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livres")
public class LivreRestController {

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @GetMapping("/{id}/exemplaires")
    public ResponseEntity<?> getLivreAvecExemplaires(@PathVariable("id") Integer id) {
        Optional<Livre> optLivre = livreRepository.findById(id);
        if (!optLivre.isPresent()) {
            return ResponseEntity.status(404).body("Livre introuvable");
        }

        Livre livre = optLivre.get();
        List<Exemplaire> exemplaires = exemplaireRepository.findByLivreId(livre.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("id", livre.getId());
        response.put("titre", livre.getTitre());
        response.put("auteur", livre.getAuteur());
        response.put("isbn", livre.getIsbn());
        response.put("exemplaires", exemplaires.stream().map(ex -> {
            Map<String, Object> exMap = new HashMap<>();
            exMap.put("reference", ex.getReference());
            exMap.put("statut", ex.getStatut()); // "disponible", "emprunte", "reserve"
            return exMap;
        }).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }
}
