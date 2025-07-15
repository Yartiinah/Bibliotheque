// Dans un nouveau fichier : src/main/java/com/votre_package/service/MembreService.java
package com.votre_package.service; // Remplacez par votre package

import com.votre_package.model.Membre; // Importez la classe Membre
import model.MembreDAO; // Votre DAO existant
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Service
public class MembreService {

    private final MembreDAO membreDAO;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Pour parser les dates

    public MembreService(MembreDAO membreDAO) {
        this.membreDAO = membreDAO;
    }

    // Méthode utilitaire pour convertir Map<String, Object> en objet Membre
    private Membre convertMapToMembre(Map<String, Object> membreMap) {
        if (membreMap == null) {
            return null;
        }
        Membre membre = new Membre();
        membre.setId((Integer) membreMap.get("id"));
        membre.setNom((String) membreMap.get("nom"));
        membre.setPrenom((String) membreMap.get("prenom"));
        membre.setEmail((String) membreMap.get("email"));
        membre.setAdresse((String) membreMap.get("adresse"));

        // Conversion de Date si le champ existe et est du bon type
        if (membreMap.containsKey("date_naissance") && membreMap.get("date_naissance") instanceof Date) {
            membre.setDateNaissance((Date) membreMap.get("date_naissance"));
        } else if (membreMap.containsKey("date_naissance") && membreMap.get("date_naissance") instanceof java.sql.Date) {
             membre.setDateNaissance(new Date(((java.sql.Date) membreMap.get("date_naissance")).getTime()));
        }
        if (membreMap.containsKey("date_expiration") && membreMap.get("date_expiration") instanceof Date) {
            membre.setDateExpirationAdhesion((Date) membreMap.get("date_expiration"));
        } else if (membreMap.containsKey("date_expiration") && membreMap.get("date_expiration") instanceof java.sql.Date) {
            membre.setDateExpirationAdhesion(new Date(((java.sql.Date) membreMap.get("date_expiration")).getTime()));
        }

        membre.setProfil((String) membreMap.get("profil"));
        membre.setTypeInscription((String) membreMap.get("type_inscription"));
        membre.setStatutValidation((String) membreMap.get("statut_validation"));
        return membre;
    }

    public List<Membre> getInscriptionsEnAttente() {
        try {
            return membreDAO.getInscriptionsEnAttente().stream()
                    .map(this::convertMapToMembre)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des inscriptions en attente: " + e.getMessage());
            throw new RuntimeException("Impossible de récupérer les inscriptions en attente.", e);
        }
    }

    public boolean validerMembre(int membreId) {
        try {
            // Logique métier : validation, potentiellement création adhésion
            boolean success = membreDAO.validerMembre(membreId);
            if (success) {
                // Si la validation réussit, créer une adhésion par défaut
                membreDAO.creerAdhesion(membreId); // Nécessite une méthode creerAdhesion dans MembreDAO
            }
            return success;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la validation du membre: " + e.getMessage());
            throw new RuntimeException("Impossible de valider le membre.", e);
        }
    }

    public boolean refuserMembre(int membreId) {
        try {
            return membreDAO.refuserMembre(membreId);
        } catch (SQLException e) {
            System.err.println("Erreur lors du refus du membre: " + e.getMessage());
            throw new RuntimeException("Impossible de refuser le membre.", e);
        }
    }

    public boolean inscrireMembreEnLigne(String nom, String prenom, String email, String adresse, String dateNaissanceStr, String profil) {
        try {
            Date dateNaissance = dateFormat.parse(dateNaissanceStr); // Convertir la chaîne en Date
            return membreDAO.inscrireMembre(nom, prenom, email, adresse, dateNaissance, profil, "EN_LIGNE", "EN_ATTENTE");
        } catch (SQLException | ParseException e) {
            System.err.println("Erreur lors de l'inscription en ligne du membre: " + e.getMessage());
            throw new RuntimeException("Impossible d'inscrire le membre en ligne.", e);
        }
    }

    public boolean inscrireMembreSurPlace(String nom, String prenom, String email, String adresse, String dateNaissanceStr, String profil) {
        try {
            Date dateNaissance = dateFormat.parse(dateNaissanceStr); // Convertir la chaîne en Date
            return membreDAO.inscrireMembre(nom, prenom, email, adresse, dateNaissance, profil, "SUR_PLACE", "VALIDE");
        } catch (SQLException | ParseException e) {
            System.err.println("Erreur lors de l'inscription sur place du membre: " + e.getMessage());
            throw new RuntimeException("Impossible d'inscrire le membre sur place.", e);
        }
    }

    public List<Membre> rechercheMembre(String motsCles) {
        try {
            return membreDAO.rechercheMembre(motsCles).stream()
                    .map(this::convertMapToMembre)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de membre: " + e.getMessage());
            throw new RuntimeException("Impossible de rechercher le membre.", e);
        }
    }

    public List<Membre> getAllMembres() {
        try {
            return membreDAO.getAllMembres().stream() // Assurez-vous que MembreDAO a un getAllMembres()
                    .map(this::convertMapToMembre)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les membres: " + e.getMessage());
            throw new RuntimeException("Impossible de récupérer tous les membres.", e);
        }
    }

    public Membre getMembreInfo(int membreId) {
        try {
            Map<String, Object> membreMap = membreDAO.getMembreInfo(membreId); // Assurez-vous que MembreDAO.getMembreInfo existe
            return (membreMap != null) ? convertMapToMembre(membreMap) : null;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des infos du membre: " + e.getMessage());
            throw new RuntimeException("Impossible de récupérer les informations du membre.", e);
        }
    }

    // Méthodes pour l'espace membre (préts, réservations, prolongations)
    // Celles-ci pourraient être dans un service plus global "EspaceMembreService"
    // ou bien le MembreService pourrait coordonner d'autres services (EmpruntService, ReservationService)

    public List<Map<String, Object>> getPretsDuMembre(int membreId) {
        try {
            // Note: ici je garde Map<String, Object> car il n'y a pas encore de POJO Pret
            return membreDAO.getPretsDuMembre(membreId);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des prêts du membre: " + e.getMessage());
            throw new RuntimeException("Impossible de récupérer les prêts du membre.", e);
        }
    }

     public List<Map<String, Object>> getReservationsDuMembre(int membreId) {
        try {
            // Note: ici je garde Map<String, Object> car il n'y a pas encore de POJO Reservation
            return membreDAO.getReservationsDuMembre(membreId);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des réservations du membre: " + e.getMessage());
            throw new RuntimeException("Impossible de récupérer les réservations du membre.", e);
        }
    }

    public boolean demanderProlongation(int pretId) {
        try {
            return membreDAO.demanderProlongation(pretId);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la demande de prolongation: " + e.getMessage());
            throw new RuntimeException("Impossible d'envoyer la demande de prolongation.", e);
        }
    }
}