package service;

import model.Exemplaire;
import model.Membre;
import model.Pret;
import model.StatutExemplaire;
import model.StatutPret;
import model.TypePret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ExemplaireRepository;
import repository.MembreRepository;
import repository.PretRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpruntService {

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private PretRepository pretRepository;

    @Transactional
    public boolean effectuerEmprunt(int membreId, int exemplaireId, String typePretStr) {
        Membre membre = membreRepository.findById(membreId).orElse(null);
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId).orElse(null);
        if (membre == null || exemplaire == null || membre.getEtat() != EtatMembre.ACTIF || exemplaire.getStatut() != StatutExemplaire.DISPONIBLE) {
            return false;
        }
        TypePret typePret = TypePret.valueOf(typePretStr.toUpperCase());
        Pret pret = new Pret();
        pret.setMembre(membre);
        pret.setExemplaire(exemplaire);
        pret.setTypePret(typePret);
        pret.setDateEmprunt(LocalDateTime.now());
        pret.setDateRetourPrevue(LocalDateTime.now().plusDays(typePret == TypePret.NORMAL ? 21 : 7));
        pret.setStatut(StatutPret.EN_COURS);
        exemplaire.setStatut(StatutExemplaire.EMPRUNTE);
        pretRepository.save(pret);
        exemplaireRepository.save(exemplaire);
        return true;
    }

    public List<Exemplaire> getExemplairesDisponibles() {
        return exemplaireRepository.findByStatut(StatutExemplaire.DISPONIBLE);
    }

    public List<Pret> getPretsMembre(int membreId) {
        return pretRepository.findByMembreIdAndStatut(membreId, StatutPret.EN_COURS);
    }
}