package controller;

import model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import service.UtilisateurService;

@Controller
public class AccController {

    @Autowired
    private UtilisateurService utilisateurService;

    // Affiche la page d'accueil (accueil.jsp)
    @GetMapping("/accueil")
    public String accueil() {
        return "accueil";
    }

    // Affiche le formulaire de connexion bibliothécaire (login.jsp)
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // Traite la connexion bibliothécaire
    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Utilisateur utilisateur = utilisateurService.authenticate(username, password);
        if (utilisateur != null && utilisateur.getRole().name().equals("BIBLIOTHECAIRE")) {
            session.setAttribute("username", username);
            session.setAttribute("role", utilisateur.getRole().name());
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("erreur", "Nom d'utilisateur ou mot de passe incorrect.");
            return "redirect:/login";
        }
    }

    // Affiche le tableau de bord (dashboard.jsp)
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("BIBLIOTHECAIRE")) {
            redirectAttributes.addFlashAttribute("erreur", "Accès non autorisé. Veuillez vous connecter.");
            return "redirect:/login";
        }
        model.addAttribute("username", session.getAttribute("username"));
        return "dashboard";
    }

    // Déconnexion bibliothécaire
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/accueil";
    }
}