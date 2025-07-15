// Modification du fichier existant : src/main/java/controller/MembreController.java
package controller;

import com.votre_package.model.Membre; // Importez la classe Membre
import com.votre_package.service.MembreService; // Votre nouveau service
// Si vous avez d'autres services (e.g., LivreService pour la recherche dans l'espace membre)
import com.votre_package.service.LivreService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.List;
import java.util.Map; // Gardez si vos vues utilisent encore des Maps pour certains objets
import java.util.Date; // Pour la conversion de date

@Controller
public class MembreController {

    private final MembreService membreService;
    private final LivreService livreService; // Injectez LivreService si utilisé pour la recherche de livres par membre

    // Injection des services via le constructeur
    public MembreController(MembreService membreService, LivreService livreService) {
        this.membreService = membreService;
        this.livreService = livreService;
    }

    @GetMapping("/inscription-form-en-ligne")
    public String afficherFormulaireInscription() {
        return "inscriptionForm"; // affiche le formulaire d'inscription
    }

    @GetMapping("/login-form-membre")
    public String afficherFormulaireLogin() {
        return "loginMembre"; // affiche la page de connexion membre
    }

    @PostMapping("/inscription-en-ligne")
    public String traiterInscription(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String email,
            @RequestParam String adresse,
            @RequestParam String dateNaissance, // Gardez comme String pour le formulaire
            @RequestParam String profil,
            RedirectAttributes redirectAttributes) {
        try {
            // Appelle le service pour l'inscription
            boolean success = membreService.inscrireMembreEnLigne(nom, prenom, email, adresse, dateNaissance, profil);
            if (success) {
                redirectAttributes.addFlashAttribute("message", "Inscription envoyée avec succès. En attente de validation.");
                return "redirect:/login-form-membre"; // Redirige vers la page de connexion
            } else {
                redirectAttributes.addFlashAttribute("erreur", "Échec de l'inscription. L'email est peut-être déjà utilisé.");
                return "redirect:/inscription-form-en-ligne";
            }
        } catch (RuntimeException e) { // Le service lance RuntimeException pour les erreurs SQL/parsing
            redirectAttributes.addFlashAttribute("erreur", "Erreur lors de l'inscription : " + e.getMessage());
            e.printStackTrace();
            return "redirect:/inscription-form-en-ligne";
        }
    }

    @GetMapping("/espace-membre/dashboard")
    public String dashboardMembre(HttpSession session, Model model) {
        Integer membreId = (Integer) session.getAttribute("membreId"); // Récupérez l'ID du membre de la session
        if (membreId == null) {
            return "redirect:/login-form-membre"; // Redirige si non connecté
        }
        try {
            Membre membre = membreService.getMembreInfo(membreId); // Récupère les infos du membre
            if (membre != null) {
                model.addAttribute("membre", membre);
            } else {
                 model.addAttribute("erreur", "Informations du membre non trouvées.");
            }
        } catch (RuntimeException e) {
             model.addAttribute("erreur", "Erreur lors du chargement des informations du membre : " + e.getMessage());
             e.printStackTrace();
        }
        return "dashboardMembre"; // Votre JSP du tableau de bord membre
    }


    @GetMapping("/espace-membre/prets")
    public String voirPrets(HttpSession session, Model model) {
        Integer membreId = (Integer) session.getAttribute("membreId");
        if (membreId == null) return "redirect:/login-form-membre";

        try {
            // Ici, nous récupérons une List<Map<String, Object>> car il n'y a pas encore de POJO Pret
            List<Map<String, Object>> prets = membreService.getPretsDuMembre(membreId);
            model.addAttribute("prets", prets);
        } catch (RuntimeException e) {
            model.addAttribute("erreur", "Erreur lors du chargement des prêts : " + e.getMessage());
            e.printStackTrace();
        }
        return "mesPrets"; // Votre JSP pour afficher les prêts
    }

    @PostMapping("/espace-membre/demander-prolongation")
    public String demanderProlongation(
            @RequestParam int pretId,
            HttpSession session,
            RedirectAttributes ra) {
        Integer membreId = (Integer) session.getAttribute("membreId");
        if (membreId == null) return "redirect:/login-form-membre";

        try {
            boolean success = membreService.demanderProlongation(pretId);
            if (success) {
                ra.addFlashAttribute("message", "Demande de prolongation envoyée avec succès.");
            } else {
                ra.addFlashAttribute("erreur", "Impossible de demander la prolongation (pénalité ou prêt non éligible).");
            }
        } catch (RuntimeException e) {
            ra.addFlashAttribute("erreur", "Une erreur technique est survenue lors de la demande de prolongation.");
            e.printStackTrace();
        }
        return "redirect:/espace-membre/prets";
    }

    @GetMapping("/espace-membre/reservations")
    public String voirReservations(HttpSession session, Model model) {
        Integer membreId = (Integer) session.getAttribute("membreId");
        if (membreId == null) return "redirect:/login-form-membre";

        try {
            // Ici, nous récupérons une List<Map<String, Object>> car il n'y a pas encore de POJO Reservation
            List<Map<String, Object>> reservations = membreService.getReservationsDuMembre(membreId);
            model.addAttribute("reservations", reservations);
        } catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("erreur", "Erreur lors du chargement des réservations.");
        }
        return "mesReservations"; // Votre JSP pour afficher les réservations
    }

    // Ajoutez d'autres méthodes pour la gestion des membres par le bibliothécaire si elles sont dans ce contrôleur
    // (e.g., valider/refuser membre, liste de tous les membres, recherche de membre)
    // Ces méthodes pourraient aussi être dans BibliothecaireController pour une meilleure séparation des rôles.
    @GetMapping("/membres/en-attente")
    public String getMembresEnAttente(Model model) {
        try {
            List<Membre> membresEnAttente = membreService.getInscriptionsEnAttente();
            model.addAttribute("membresEnAttente", membresEnAttente);
            return "listeMembresEnAttente"; // JSP pour afficher les membres en attente
        } catch (RuntimeException e) {
            model.addAttribute("erreur", "Erreur lors du chargement des membres en attente: " + e.getMessage());
            e.printStackTrace();
            return "listeMembresEnAttente";
        }
    }

    @PostMapping("/membres/valider")
    public String validerMembre(@RequestParam int id, RedirectAttributes ra) {
        try {
            boolean success = membreService.validerMembre(id);
            if (success) {
                ra.addFlashAttribute("message", "Membre validé avec succès.");
            } else {
                ra.addFlashAttribute("erreur", "Échec de la validation du membre.");
            }
        } catch (RuntimeException e) {
            ra.addFlashAttribute("erreur", "Erreur lors de la validation : " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/membres/en-attente";
    }

    @PostMapping("/membres/refuser")
    public String refuserMembre(@RequestParam int id, RedirectAttributes ra) {
        try {
            boolean success = membreService.refuserMembre(id);
            if (success) {
                ra.addFlashAttribute("message", "Membre refusé avec succès.");
            } else {
                ra.addFlashAttribute("erreur", "Échec du refus du membre.");
            }
        } catch (RuntimeException e) {
            ra.addFlashAttribute("erreur", "Erreur lors du refus : " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/membres/en-attente";
    }

    // Pour la recherche de membre, si elle est dans MembreController
    @GetMapping("/recherche-membre")
    public String rechercheMembre(@RequestParam(required = false) String mots, Model model) {
        if (mots == null || mots.trim().isEmpty()) {
            model.addAttribute("resultats", membreService.getAllMembres()); // Ou une liste vide
            return "rechercheMembre"; // JSP pour les résultats de recherche membre
        }
        model.addAttribute("resultats", membreService.rechercheMembre(mots));
        return "rechercheMembre";
    }

    // Vous pouvez également avoir la recherche de livres ici si le membre peut rechercher des livres
    @GetMapping("/recherche-livre")
    public String rechercheLivreMembre(@RequestParam(required = false) String mots, Model model) {
        if (mots == null || mots.trim().isEmpty()) {
            model.addAttribute("resultats", livreService.getAllLivres()); // Utilise LivreService
            return "rechercheLivrePourMembre"; // JSP pour les résultats de recherche de livre
        }
        model.addAttribute("resultats", livreService.rechercheLivre(mots));
        return "rechercheLivrePourMembre";
    }
}