package controller;

import model.Adherent;
import repository.TypeAbonnementRepository;
import service.AdherentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/adherents")
public class AdherentController {
    @Autowired
    private AdherentService adherentService;

    @Autowired
    private TypeAbonnementRepository typeAbonnementRepository;

    @GetMapping("/ajouter")
    public String showForm(Model model) {
        model.addAttribute("adherent", new Adherent());
        model.addAttribute("typesAbonnement", typeAbonnementRepository.findAll());
        return "ajouterAdherent";
    }

    @PostMapping("/ajouter")
    public String ajouterAdherent(@ModelAttribute("adherent") Adherent adherent,
                                  @RequestParam("dateInscription") String dateInscriptionStr,
                                  @RequestParam("dateExpiration") String dateExpirationStr,
                                  Model model) {
        Integer typeAbonnementId = adherent.getTypeAbonnement() != null ? adherent.getTypeAbonnement().getId() : null;
        LocalDate dateInscription = LocalDate.parse(dateInscriptionStr);
        LocalDate dateExpiration = LocalDate.parse(dateExpirationStr);
        String message = adherentService.inscrireAdherent(adherent, typeAbonnementId, dateInscription, dateExpiration);
        model.addAttribute("message", message);
        model.addAttribute("typesAbonnement", typeAbonnementRepository.findAll());
        return "ajouterAdherent";
    }

    @GetMapping("/home")
    public String homeAdherent() {
        return "homeAdherent";
    }

}
