package service;

import model.Inscription;
import model.Membre;
import model.StatutInscription;
import model.TypeAbonnement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.InscriptionRepository;
import repository.MembreRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MembreService {

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    public Membre loginMembre(String email, String nom) {
        Membre membre = membreRepository.findByEmailAndNom(email, nom);
        if (membre == null) {
            return null;
        }
        Inscription inscription = inscriptionRepository.findByMembreIdAndStatut(membre.getId(), StatutInscription.VALIDE);
        return inscription != null ? membre : null;
    }

    @Transactional
    public boolean inscriptionADistance(String nom, String prenom, String email, String adresse, LocalDateTime dateNaissance, TypeAbonnement typeAbonnement) {
        Membre membre = new Membre();
        membre.setNom(nom);
        membre.setPrenom(prenom);
        membre.setEmail(email);
        membre.setAdresse(adresse);
        membre.setDateNaissance(dateNaissance);
        membre.setTypeAbonnement(typeAbonnement);
        membre.setEtat(EtatMembre.ACTIF);
        membreRepository.save(membre);

        Inscription inscription = new Inscription();
        inscription.setMembre(membre);
        inscription.setDateInscription(LocalDateTime.now());
        inscription.setStatut(StatutInscription.EN_ATTENTE);
        inscriptionRepository.save(inscription);
        return true;
    }

    public List<Membre> getInscriptionsEnAttente() {
        return inscriptionRepository.findByStatut(StatutInscription.EN_ATTENTE)
                .stream()
                .map(Inscription::getMembre)
                .collect(Collectors.toList());
    }

    @Transactional
    public void validerInscription(int inscriptionId, double montantCotisation) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId).orElseThrow(() -> new IllegalArgumentException("Inscription non trouvée"));
        inscription.setStatut(StatutInscription.VALIDE);
        inscription.setMontantCotisation(montantCotisation);
        inscriptionRepository.save(inscription);
        Membre membre = inscription.getMembre();
        membre.setEtat(EtatMembre.ACTIF);
        membreRepository.save(membre);
    }

    @Transactional
    public void refuserInscription(int inscriptionId) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId).orElseThrow(() -> new IllegalArgumentException("Inscription non trouvée"));
        inscription.setStatut(StatutInscription.REFUSEE);
        inscriptionRepository.save(inscription);
        Membre membre = inscription.getMembre();
        membre.setEtat(EtatMembre.INACTIF);
        membreRepository.save(membre);
    }

    public boolean isInscriptionValide(int membreId) {
        Inscription inscription = inscriptionRepository.findByMembreIdAndStatut(membreId, StatutInscription.VALIDE);
        if (inscription == null) {
            return false;
        }
        LocalDateTime dateExpiration = inscription.getDateInscription().plusYears(1);
        return LocalDateTime.now().isBefore(dateExpiration);
    }
}