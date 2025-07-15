
// package controller;

// import model.Adherent;
// import model.Penalite;
// import model.TypeAbonnement;
// import repository.AdherentRepository;
// import repository.PenaliteRepository;
// import repository.PretRepository;
// import service.AdherentService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.time.LocalDateTime;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/adherents")
// public class AdherentRestController {

//     @Autowired
//     private AdherentRepository adherentRepository;

//     @Autowired
//     private PretRepository pretRepository;

//     @Autowired
//     private PenaliteRepository penaliteRepository;

//     @Autowired
//     private AdherentService adherentService;

//     @GetMapping("/{id}")
//     public ResponseEntity<?> getAdherentDetails(@PathVariable("id") Integer id) {
//         Optional<Adherent> optAdherent = adherentRepository.findById(id);
//         if (!optAdherent.isPresent()) {
//             return ResponseEntity.status(404).body("Adhérent introuvable");
//         }

//         Adherent adherent = optAdherent.get();
//         TypeAbonnement abonnement = adherent.getTypeAbonnement();

//         // Calcul du quota restant
//         int quotaPret = abonnement.getQuota_pret(); // Corrigé : quota_pret au lieu de quotaPret
//         long pretsEnCours = pretRepository.findByAdherentId(adherent.getId()).stream()
//                 .filter(pret -> "en_cours".equals(pret.getStatut()))
//                 .count();
//         int quotaRestant = quotaPret - (int) pretsEnCours;

//         // Vérification des pénalités actives
//         Penalite penaliteActive = penaliteRepository.findFirstByAdherentAndDateFinAfterOrderByDateFinDesc(
//                 adherent, LocalDateTime.now());
//         boolean estPenalise = penaliteActive != null;
//         String dateFinPenalite = estPenalise ? penaliteActive.getDateFin().toString() : null;

//         // Construction de la réponse JSON
//         Map<String, Object> response = new HashMap<>();
//         response.put("id", adherent.getId());
//         response.put("nom", adherent.getNom());
//         response.put("prenom", adherent.getPrenom());
//         response.put("email", adherent.getEmail()); // À supprimer si email n'existe pas
//         response.put("abonnement", Map.of(
//                 "nom", abonnement.getNom_abonnement(), // Corrigé : nom_abonnement au lieu de nom
//                 "dateExpiration", adherentService.isInscriptionValide(adherent) ? 
//                         adherent.getDate_expiration().toString() : "Expiré", // Corrigé : date_expiration
//                 "quotaPret", quotaPret
//         ));
//         response.put("quotaRestant", quotaRestant);
//         response.put("penaliteActive", estPenalise);
//         if (estPenalise) {
//             response.put("dateFinPenalite", dateFinPenalite);
//         }

//         return ResponseEntity.ok(response);
//     }
// }