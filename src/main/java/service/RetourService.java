package service;

import model.Pret;
import model.Penalite;
import repository.PretRepository;
import repository.PenaliteRepository;
import repository.ExemplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RetourService {

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private PenaliteRepository penaliteRepository;

    @Transactional
    public boolean enregistrerRetour(int pretId) {
        Pret pret = pretRepository.findById(pretId)
                .orElseThrow(() -> new IllegalArgumentException("Prêt non trouvé"));
        if (pret.getDateRetourEffective() != null) {
            return false;
        }
        pret.setDateRetourEffective(LocalDateTime.now());
        pret.setStatut(StatutPret.TERMINE);
        pret.getExemplaire().setStatut(StatutExemplaire.DISPONIBLE);
        pretRepository.save(pret);
        exemplaireRepository.save(pret.getExemplaire());

        if (pret.getDateRetourPrevue().isBefore(LocalDateTime.now())) {
            Penalite penalite = new Penalite();
            penalite.setMembre(pret.getMembre());
            penalite.setPret(pret);
            penalite.setDateDebut(LocalDateTime.now());
            penalite.setDateFin(LocalDateTime.now().plusDays(10));
            penalite.setReglee(false);
            penaliteRepository.save(penalite);
        }
        return true;
    }

    public List<Map<String, Object>> getPretsEnCours() {
        return pretRepository.findByDateRetourEffectiveIsNull().stream()
                .map(p -> Map.of(
                        "pret_id", p.getId(),
                        "date_emprunt", p.getDateEmprunt(),
                        "date_retour_prevue", p.getDateRetourPrevue(),
                        "membre_nom", p.getMembre().getNom(),
                        "membre_prenom", p.getMembre().getPrenom(),
                        "livre_titre", p.getExemplaire().getLivre().getTitre(),
                        "code_exemplaire", p.getExemplaire().getReference(),
                        "jours_retard", p.getDateRetourPrevue().isBefore(LocalDateTime.now()) ?
                                ChronoUnit.DAYS.between(p.getDateRetourPrevue(), LocalDateTime.now()) : 0))
                .collect(Collectors.toList());
    }
}