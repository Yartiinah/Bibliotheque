package service;

import model.Exemplaire;
import model.Membre;
import model.Pret;
import model.TypePret;
import repository.ExemplaireRepository;
import repository.MembreRepository;
import repository.PretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmpruntService {

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private PretRepository pretRepository;

    public Map<String, Object> getMembreInfo(int membreId) {
        Membre membre = membreRepository.findById(membreId)
                .orElse(null);
        if (membre == null || !membre.getEtat().equals(EtatMembre.ACTIF)) {
            return null;
        }
        return Map.of(
                "id", membre.getId(),
                "nom", membre.getNom(),
                "prenom", membre.getPrenom(),
                "email", membre.getEmail(),
                "profil", membre.getTypeAbonnement().getLibelle().name());
    }

    public boolean aPenaliteEnCours(int membreId) {
        return pretRepository.findByMembreIdAndDateFinPenaliteAfter(membreId, LocalDateTime.now()).size() > 0;
    }

    public int nombreEmpruntsEnCours(int membreId) {
        return pretRepository.findByMembreIdAndDateRetourEffectiveIsNull(membreId).size();
    }

    public List<Map<String, Object>> getExemplairesDisponibles() {
        return exemplaireRepository.findByStatut(StatutExemplaire.DISPONIBLE).stream()
                .map(e -> Map.of(
                        "id", e.getId(),
                        "code_exemplaire", e.getReference(),
                        "titre", e.getLivre().getTitre(),
                        "auteur", e.getLivre().getAuteur(),
                        "categorie", e.getLivre().getCategorie().getNom()))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean effectuerEmprunt(int membreId, int exemplaireId, String typePret) {
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new IllegalArgumentException("Membre non trouvé"));
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId)
                .orElseThrow(() -> new IllegalArgumentException("Exemplaire non trouvé"));

        if (!exemplaire.getStatut().equals(StatutExemplaire.DISPONIBLE)) {
            return false;
        }

        exemplaire.setStatut(StatutExemplaire.EMPRUNTE);
        exemplaireRepository.save(exemplaire);

        Pret pret = new Pret();
        pret.setMembre(membre);
        pret.setExemplaire(exemplaire);
        pret.setDateEmprunt(LocalDateTime.now());
        pret.setDateRetourPrevue(LocalDateTime.now().plusDays(membre.getTypeAbonnement().getDureePretJour()));
        pret.setTypePret(TypePret.valueOf(typePret.toUpperCase()));
        pret.setStatut(StatutPret.EN_COURS);
        pretRepository.save(pret);

        return true;
    }
}