package service;

import model.Pret;
import model.StatutExemplaire;
import model.StatutPret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ExemplaireRepository;
import repository.PretRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RetourService {

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Transactional
    public void enregistrerRetour(int pretId) {
        Pret pret = pretRepository.findById(pretId).orElseThrow(() -> new IllegalArgumentException("Prêt non trouvé"));
        pret.setStatut(StatutPret.TERMINE);
        pret.setDateRetour(LocalDateTime.now());
        pret.getExemplaire().setStatut(StatutExemplaire.DISPONIBLE);
        pretRepository.save(pret);
        exemplaireRepository.save(pret.getExemplaire());
    }

    public List<Pret> getPretsEnCours() {
        return pretRepository.findByStatut(StatutPret.EN_COURS);
    }
}