package service;

import model.Prolongation;
import model.Pret;
import repository.ProlongationRepository;
import repository.PretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProlongationService {

    @Autowired
    private ProlongationRepository prolongationRepository;

    @Autowired
    private PretRepository pretRepository;

    public boolean peutDemanderProlongation(int membreId) {
        return pretRepository.findByMembreIdAndDateFinPenaliteAfter(membreId, LocalDateTime.now()).isEmpty();
    }

    @Transactional
    public boolean demanderProlongation(int pretId, int membreId) {
        if (!peutDemanderProlongation(membreId)) {
            return false;
        }
        Pret pret = pretRepository.findById(pretId)
                .orElseThrow(() -> new IllegalArgumentException("Prêt non trouvé"));
        if (pret.getDateRetourEffective() != null || pret.getDateRetourPrevue().isBefore(LocalDateTime.now())) {
            return false;
        }
        Prolongation prolongation = new Prolongation();
        prolongation.setPret(pret);
        prolongation.setDateDemande(LocalDateTime.now());
        prolongation.setStatut(StatutProlongation.EN_ATTENTE);
        prolongationRepository.save(prolongation);
        return true;
    }

    public List<Map<String, Object>> getDemandesProlongation() {
        return prolongationRepository.findByStatut(StatutProlongation.EN_ATTENTE).stream()
                .map(p -> Map.of(
                        "pretId", p.getPret().getId(),
                        "nom", p.getPret().getMembre().getNom(),
                        "prenom", p.getPret().getMembre().getPrenom(),
                        "titre", p.getPret().getExemplaire().getLivre().getTitre(),
                        "dateRetour", p.getPret().getDateRetourPrevue()))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean traiterProlongation(int pretId, boolean accepter) {
        Prolongation prolongation = prolongationRepository.findByPretIdAndStatut(pretId, StatutProlongation.EN_ATTENTE)
                .orElseThrow(() -> new IllegalArgumentException("Prolongation non trouvée"));
        if (accepter) {
            Pret pret = prolongation.getPret();
            pret.setDateRetourPrevue(pret.getDateRetourPrevue().plusDays(10));
            pret.setNbProlongements(pret.getNbProlongements() + 1);
            pretRepository.save(pret);
            prolongation.setStatut(StatutProlongation.ACCEPTEE);
        } else {
            prolongation.setStatut(StatutProlongation.REFUSEE);
        }
        prolongationRepository.save(prolongation);
        return true;
    }
}