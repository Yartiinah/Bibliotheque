package service;

import model.Adherent;
import model.TypeAbonnement;
import model.Inscription;
import repository.AdherentRepository;
import repository.TypeAbonnementRepository;
import repository.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdherentService {
    @Autowired
    private AdherentRepository adherentRepository;
    @Autowired
    private TypeAbonnementRepository typeAbonnementRepository;
    @Autowired
    private InscriptionRepository inscriptionRepository;

    public String inscrireAdherent(Adherent adherent, Integer typeAbonnementId, LocalDate dateInscription, LocalDate dateExpiration) {
        if (adherent == null || typeAbonnementId == null ||
            adherent.getNom() == null || adherent.getPrenom() == null || adherent.getEmail() == null || adherent.getAdresse() == null ||
            adherent.getNom().isEmpty() || adherent.getPrenom().isEmpty() || adherent.getEmail().isEmpty() || adherent.getAdresse().isEmpty()) {
            return "Veuillez compléter tous les champs requis";
        }
        if (adherentRepository.findByEmail(adherent.getEmail()) != null) {
            return "Cet email est déjà utilisé";
        }
        TypeAbonnement typeAbonnement = typeAbonnementRepository.findById(typeAbonnementId).orElse(null);
        if (typeAbonnement == null) {
            return "Type d'abonnement inconnu";
        }
        adherent.setTypeAbonnement(typeAbonnement);
        adherent.setEtat("actif");
        adherentRepository.save(adherent);

        Inscription inscription = new Inscription();
        inscription.setAdherent(adherent);
        inscription.setDateInscription(dateInscription.atStartOfDay());
        inscription.setDateExpiration(dateExpiration.atStartOfDay());
        inscription.setStatut("valide");
        inscriptionRepository.save(inscription);

        return "Adhérent inscrit avec succès. Veuillez procéder au paiement de la cotisation.";
    }

    public boolean isInscriptionValide(Adherent adherent) {
        List<model.Inscription> inscriptions = inscriptionRepository.findByAdherentId(adherent.getId());
        java.time.LocalDate today = java.time.LocalDate.now();
        for (model.Inscription insc : inscriptions) {
            java.time.LocalDate debut = insc.getDateInscription().toLocalDate();
            java.time.LocalDate fin = insc.getDateExpiration().toLocalDate();
            if ((debut.isEqual(today) || debut.isBefore(today)) && (fin.isEqual(today) || fin.isAfter(today)) && "valide".equals(insc.getStatut())) {
                return true;
            }
        }
        return false;
    }
}
