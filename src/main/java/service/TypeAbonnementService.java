package service;

import model.TypeAbonnement;
import repository.TypeAbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeAbonnementService {
    @Autowired
    private TypeAbonnementRepository typeAbonnementRepository;

    public TypeAbonnement getById(int id) {
        return typeAbonnementRepository.findById(id).orElse(null);
    }

    public TypeAbonnement updateTypeAbonnement(int id, String libelle, double tarif, int quotaLivre, int dureePretJour, int quotaReservation, int quotaProlongement, int nbJourProlongement) {
        TypeAbonnement abo = typeAbonnementRepository.findById(id).orElse(null);
        if (abo == null) return null;
        abo.setLibelle(libelle);
        abo.setTarif(tarif);
        abo.setQuotaLivre(quotaLivre);
        abo.setDureePretJour(dureePretJour);
        abo.setQuotaReservation(quotaReservation);
        abo.setQuotaProlongement(quotaProlongement);
        abo.setNbJourProlongement(nbJourProlongement);
        return typeAbonnementRepository.save(abo);
    }
}