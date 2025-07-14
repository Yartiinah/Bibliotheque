package service;

import model.EtatMembre;
import model.Inscription;
import model.Membre;
import model.TypeAbonnement;
import repository.InscriptionRepository;
import repository.MembreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MembreService {

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    public List<Map<String, Object>> getInscriptionsEnAttente() {
        return membreRepository.findByStatutValidation("EN_ATTENTE").stream()
                .map(m -> Map.of(
                        "id", m.getId(),
                        "nom", m.getNom(),
                        "prenom", m.getPrenom(),
                        "email", m.getEmail()))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getAllMembres() {
        return membreRepository.findByStatutValidation("VALIDE").stream()
                .map(m -> Map.of(
                        "id", m.getId(),
                        "nom", m.getNom(),
                        "prenom", m.getPrenom(),
                        "email", m.getEmail()))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> rechercheMembre(String motCle) {
        return membreRepository.rechercheMembre(motCle).stream()
                .map(m -> Map.of(
                        "id", m.getId(),
                        "nom", m.getNom(),
                        "prenom", m.getPrenom(),
                        "email", m.getEmail()))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean creerMembreSurPlace(String nom, String prenom, String email, String adresse,
                                      LocalDateTime dateNaissance, TypeAbonnement typeAbonnement,
                                      double montantCotisation) {
        Membre membre = new Membre();
        membre.setNom(nom);
        membre.setPrenom(prenom);
        membre.setEmail(email);
        membre.setAdresse(adresse);
        membre.setDateNaissance(dateNaissance);
        membre.setTypeAbonnement(typeAbonnement);
        membre.setEtat(EtatMembre.ACTIF);

        membre = membreRepository.save(membre);

        Inscription inscription = new Inscription();
        inscription.setMembre(membre);
        inscription.setDateInscription(LocalDateTime.now());
        inscription.setDateExpiration(LocalDateTime.now().plusYears(1));
        inscription.setStatut(StatutInscription.VALIDE);
        inscriptionRepository.save(inscription);

        return true;
    }

    @Transactional
    public boolean inscriptionADistance(String nom, String prenom, String email, String adresse,
                                       LocalDateTime dateNaissance, TypeAbonnement typeAbonnement) {
        Membre membre = new Membre();
        membre.setNom(nom);
        membre.setPrenom(prenom);
        membre.setEmail(email);
        membre.setAdresse(adresse);
        membre.setDateNaissance(dateNaissance);
        membre.setTypeAbonnement(typeAbonnement);
        membre.setEtat(EtatMembre.BLOQUE); // En attente de validation
        membreRepository.save(membre);
        return true;
    }

    @Transactional
    public boolean accepterInscription(int membreId, double montantCotisation) {
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new IllegalArgumentException("Membre non trouvé"));
        membre.setEtat(EtatMembre.ACTIF);
        membreRepository.save(membre);

        Inscription inscription = new Inscription();
        inscription.setMembre(membre);
        inscription.setDateInscription(LocalDateTime.now());
        inscription.setDateExpiration(LocalDateTime.now().plusYears(1));
        inscription.setStatut(StatutInscription.VALIDE);
        inscriptionRepository.save(inscription);

        return true;
    }

    @Transactional
    public boolean refuserInscription(int membreId) {
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new IllegalArgumentException("Membre non trouvé"));
        membre.setEtat(EtatMembre.BLOQUE);
        membreRepository.save(membre);
        return true;
    }

    public Map<String, Object> loginMembre(String email, String nom) {
        Membre membre = membreRepository.findByEmailAndNomAndStatutValidation(email, nom, "VALIDE");
        if (membre != null && membre.getEtat() == EtatMembre.ACTIF) {
            return Map.of(
                    "id", membre.getId(),
                    "nom", membre.getNom(),
                    "prenom", membre.getPrenom(),
                    "email", membre.getEmail());
        }
        return null;
    }

    public boolean isInscriptionValide(int membreId) {
        Membre membre = membreRepository.findById(membreId)
                .orElse(null);
        if (membre == null) {
            return false;
        }
        List<Inscription> inscriptions = inscriptionRepository.findByMembreId(membreId);
        return inscriptions.stream()
                .anyMatch(inscription ->
                        inscription.getStatut() == StatutInscription.VALIDE &&
                        inscription.getDateExpiration().isAfter(LocalDateTime.now()));
    }
}