package service;

import model.Pret;
import model.Prolongation;
import model.StatutProlongation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.PretRepository;
import repository.ProlongationRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProlongationService {

    @Autowired
    private ProlongationRepository prolongationRepository;

    @Autowired
    private PretRepository pretRepository;

    @Transactional
    public boolean demanderProlongation(int pretId, int membreId) {
        Pret pret = pretRepository.findById(pretId).orElse(null);
        if (pret == null || pret.getMembre().getId() != membreId) {
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
        return prolongationRepository.findByStatut(StatutProlongation.EN_ATTENTE).stream().map(prolongation -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", prolongation.getId());
            map.put("pretId", prolongation.getPret().getId());
            map.put("membreNom", prolongation.getPret().getMembre().getNom());
            map.put("titreLivre", prolongation.getPret().getExemplaire().getLivre().getTitre());
            map.put("dateDemande", prolongation.getDateDemande());
            return map;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void traiterProlongation(int pretId, boolean accepter) {
        Prolongation prolongation = prolongationRepository.findByPretIdAndStatut(pretId, StatutProlongation.EN_ATTENTE).orElse(null);
        if (prolongation == null) {
            throw new IllegalStateException("Prolongation non trouvée ou déjà traitée.");
        }
        prolongation.setStatut(accepter ? StatutProlongation.ACCEPTEE : StatutProlongation.REFUSEE);
        if (accepter) {
            Pret pret = prolongation.getPret();
            pret.setDateRetourPrevue(pret.getDateRetourPrevue().plusDays(14));
            pretRepository.save(pret);
        }
        prolongationRepository.save(prolongation);
    }
}