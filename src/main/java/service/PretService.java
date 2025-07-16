package service;

import model.Pret;
import model.Exemplaire;
import model.Adherent;
import model.TypeAbonnement;
import model.HistoriquePret;
import service.HistoriquePretService;
import service.JourFerieService;
import repository.PretRepository;
import repository.ExemplaireRepository;
import repository.AdherentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import model.Penalite;
import repository.PenaliteRepository;

@Service
public class PretService {
    @Autowired
    public PretRepository pretRepository;
    @Autowired
    private ExemplaireRepository exemplaireRepository;
    @Autowired
    private AdherentRepository adherentRepository;
    @Autowired
    private HistoriquePretService historiquePretService;
    @Autowired
    private AdherentService adherentService;
    @Autowired
    private JourFerieService jourFerieService;
    @Autowired
    private PenaliteRepository penaliteRepository;
    @Autowired
    private repository.ReservationRepository reservationRepository;

    public String creerPret(Integer adherentId, String referenceExemplaire, String typePret, String dateEmpruntStr, Integer reservationId) {
        Optional<Adherent> adherentOpt = adherentRepository.findById(adherentId);
        if (adherentOpt.isEmpty()) return "Adhérent introuvable";
        Adherent adherent = adherentOpt.get();
        if (!adherentService.isInscriptionValide(adherent)) {
            return "Votre abonnement n'est plus valide. Veuillez le renouveler.";
        }
        if (!"actif".equals(adherent.getEtat())) return "Veuillez renouveler votre abonnement";

        if (penaliteRepository.existsByAdherentAndDateFinAfter(adherent, LocalDateTime.now())) {
            return "Cet adhérent est pénalisé jusqu'à la date " +
                   penaliteRepository.findFirstByAdherentAndDateFinAfterOrderByDateFinDesc(adherent, LocalDateTime.now()).getDateFin();
        }

        Optional<Exemplaire> exemplaireOpt = exemplaireRepository.findByReference(referenceExemplaire);
        if (exemplaireOpt.isEmpty()) return "Exemplaire introuvable";
        Exemplaire exemplaire = exemplaireOpt.get();
        if (!"disponible".equals(exemplaire.getStatut())) return "Cet exemplaire n'est pas empruntable";

        TypeAbonnement abo = adherent.getTypeAbonnement();
        int duree = abo.getDureePretJour();
        LocalDateTime dateEmprunt = (dateEmpruntStr != null && !dateEmpruntStr.isEmpty()) ? LocalDate.parse(dateEmpruntStr).atStartOfDay() : LocalDateTime.now();
        LocalDateTime retour = jourFerieService.ajusterDateRetour(dateEmprunt.plusDays(duree));
        Pret pret = new Pret();
        pret.setAdherent(adherent);
        pret.setExemplaire(exemplaire);
        pret.setDateEmprunt(dateEmprunt);
        pret.setDateRetourPrevue(retour);
        pret.setTypePret(typePret);
        pret.setStatut("en_cours");
        pretRepository.save(pret);
        exemplaire.setStatut("emprunte");
        exemplaireRepository.save(exemplaire);
        HistoriquePret hist = new HistoriquePret();
        hist.setPret(pret);
        hist.setAction("emprunt");
        hist.setDateAction(dateEmprunt);
        hist.setCommentaire("Prêt créé");
        historiquePretService.save(hist);
        return "Prêt enregistré avec succès. Date de retour : " + retour;
    }

    public String prolongerPret(Integer pretId, Integer adherentId, String nouvelleDateRetourPrevueStr) {
        Pret pret = pretRepository.findById(pretId).orElse(null);
        if (pret == null || !"en_cours".equals(pret.getStatut())) {
            return "Prêt introuvable ou non prolongeable";
        }
        // Vérification de l'adhérent uniquement si adherentId est fourni (pour les non-bibliothécaires)
        if (adherentId != null && pret.getAdherent().getId() != adherentId) {
            return "Vous ne pouvez prolonger que vos propres prêts";
        }
        Adherent adherent = pret.getAdherent();
        if (!adherentService.isInscriptionValide(adherent)) {
            return "Votre abonnement n'est plus valide. Veuillez le renouveler.";
        }
        if (!"actif".equals(adherent.getEtat())) {
            return "Vous êtes pénalisé, prolongement impossible";
        }
        TypeAbonnement abo = adherent.getTypeAbonnement();
        if (pret.getNbProlongements() >= abo.getQuotaProlongement()) {
            return "Quota de prolongements atteint";
        }

        // Parse and validate the new return date
        LocalDateTime nouvelleDateRetourPrevue;
        try {
            nouvelleDateRetourPrevue = LocalDate.parse(nouvelleDateRetourPrevueStr).atStartOfDay();
        } catch (Exception e) {
            throw new IllegalArgumentException("Format de date invalide. Veuillez utiliser AAAA-MM-JJ.");
        }

        // Validate that the new return date is after the current return date
        LocalDateTime ancienneDateRetour = pret.getDateRetourPrevue();
        if (!nouvelleDateRetourPrevue.isAfter(ancienneDateRetour)) {
            throw new IllegalArgumentException("La nouvelle date de retour doit être postérieure à la date de retour actuelle (" + ancienneDateRetour.toLocalDate() + ").");
        }

        // Vérification des réservations sur la période de prolongation
        List<model.Reservation> reservations = reservationRepository.findByExemplaireId(pret.getExemplaire().getId());
        for (model.Reservation r : reservations) {
            if (("en_attente".equals(r.getStatut()) || "acceptee".equals(r.getStatut()))
                && r.getAdherent().getId() != adherent.getId()
                && !r.getDateDemande().isBefore(ancienneDateRetour)
                && !r.getDateDemande().isAfter(nouvelleDateRetourPrevue)) {
                return "Impossible de prolonger : une réservation existe sur la période de prolongation (" + r.getAdherent().getNom() + ", date : " + r.getDateDemande().toLocalDate() + ")";
            }
        }

        // Mettre à jour la date de retour prévue et le nombre de prolongements
        pret.setDateRetourPrevue(nouvelleDateRetourPrevue);
        pret.setNbProlongements(pret.getNbProlongements() + 1);
        pretRepository.save(pret);

        // Historique : prolongement
        HistoriquePret hist = new HistoriquePret();
        hist.setPret(pret);
        hist.setAction("prolongement");
        hist.setDateAction(LocalDateTime.now());
        hist.setCommentaire("Prolongement validé à " + nouvelleDateRetourPrevue.toLocalDate());
        historiquePretService.save(hist);

        return "Prolongement effectué avec succès jusqu'au " + nouvelleDateRetourPrevue.toLocalDate();
    }

    public String retourPret(Integer pretId) {
        Pret pret = pretRepository.findById(pretId).orElse(null);
        if (pret == null) return "Prêt introuvable";
        Adherent adherent = pret.getAdherent();
        Exemplaire exemplaire = pret.getExemplaire();
        exemplaire.setStatut("disponible");
        pret.setDateRetourEffective(LocalDateTime.now());
        pret.setStatut("termine");
        pretRepository.save(pret);
        exemplaireRepository.save(exemplaire);
        HistoriquePret hist = new HistoriquePret();
        hist.setPret(pret);
        hist.setAction("retour");
        hist.setDateAction(LocalDateTime.now());
        hist.setCommentaire("Retour effectué");
        historiquePretService.save(hist);

        if (pret.getDateRetourPrevue() != null && LocalDateTime.now().isAfter(pret.getDateRetourPrevue())) {
            Penalite penalite = new Penalite();
            penalite.setAdherent(adherent);
            penalite.setPret(pret);
            penalite.setDateDebut(LocalDateTime.now());
            penalite.setDateFin(LocalDateTime.now().plusDays(10));
            penalite.setReglee(false);
            penaliteRepository.save(penalite);
        }

        return "Retour enregistré avec succès pour le prêt ID " + pretId;
    }

    public String modifierDateRetourEffective(Integer pretId, String nouvelleDateRetourEffectiveStr) {
        Pret pret = pretRepository.findById(pretId).orElse(null);
        if (pret == null || !"en_cours".equals(pret.getStatut())) {
            return "Prêt introuvable ou non modifiable";
        }
        LocalDateTime nouvelleDateRetourEffective;
        try {
            nouvelleDateRetourEffective = LocalDate.parse(nouvelleDateRetourEffectiveStr).atStartOfDay();
        } catch (Exception e) {
            throw new IllegalArgumentException("Format de date invalide. Veuillez utiliser AAAA-MM-JJ.");
        }
        if (nouvelleDateRetourEffective.isBefore(pret.getDateEmprunt())) {
            throw new IllegalArgumentException("La date de retour effective doit être après la date d'emprunt.");
        }
        Adherent adherent = pret.getAdherent();
        Exemplaire exemplaire = pret.getExemplaire();
        exemplaire.setStatut("disponible");
        pret.setDateRetourEffective(nouvelleDateRetourEffective);
        pret.setStatut("termine");
        pretRepository.save(pret);
        exemplaireRepository.save(exemplaire);
        HistoriquePret hist = new HistoriquePret();
        hist.setPret(pret);
        hist.setAction("retour_effectif");
        hist.setDateAction(LocalDateTime.now());
        hist.setCommentaire("Date de retour effective enregistrée à " + nouvelleDateRetourEffective);
        historiquePretService.save(hist);
        if (pret.getDateRetourPrevue() != null && nouvelleDateRetourEffective.isAfter(pret.getDateRetourPrevue())) {
            Penalite penalite = new Penalite();
            penalite.setAdherent(adherent);
            penalite.setPret(pret);
            penalite.setDateDebut(LocalDateTime.now());
            penalite.setDateFin(LocalDateTime.now().plusDays(10));
            penalite.setReglee(false);
            penaliteRepository.save(penalite);
        }
        return "Retour effectif enregistré avec succès à " + nouvelleDateRetourEffective.toLocalDate();
    }

    public List<Pret> getPretsByAdherent(Integer adherentId) {
        return pretRepository.findAll().stream()
            .filter(p -> p.getAdherent().getId() == adherentId)
            .toList();
    }

    public List<Pret> getPretsByAdherentId(Integer adherentId) {
        return pretRepository.findByAdherentId(adherentId);
    }

    public void creerPretDepuisReservation(model.Reservation reservation, String typePret) {
        model.Pret pret = new model.Pret();
        pret.setAdherent(reservation.getAdherent());
        pret.setExemplaire(reservation.getExemplaire());
        pret.setDateEmprunt(reservation.getDateDemande());
        int duree = reservation.getAdherent().getTypeAbonnement().getDureePretJour();
        pret.setDateRetourPrevue(reservation.getDateDemande().plusDays(duree));
        pret.setStatut("en_cours");
        pret.setTypePret(typePret);
        pretRepository.save(pret);
        model.Exemplaire ex = reservation.getExemplaire();
        ex.setStatut("emprunte");
        exemplaireRepository.save(ex);
    }

    public Pret findPretByExemplaireAndDate(String referenceExemplaire, String dateEmpruntStr) {
        Optional<Exemplaire> exemplaireOpt = exemplaireRepository.findByReference(referenceExemplaire);
        if (exemplaireOpt.isEmpty()) {
            return null;
        }
        if (dateEmpruntStr == null || dateEmpruntStr.trim().isEmpty()) {
            return pretRepository.findByExemplaireReferenceAndStatut(referenceExemplaire, "en_cours");
        }
        try {
            LocalDateTime dateEmprunt = LocalDate.parse(dateEmpruntStr).atStartOfDay();
            return pretRepository.findByExemplaireReferenceAndDateEmprunt(referenceExemplaire, dateEmprunt);
        } catch (Exception e) {
            return null;
        }
    }
}