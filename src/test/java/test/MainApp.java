package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.JourFerieService;  // adapte selon ton package

import java.time.LocalDateTime;

public class MainApp {
    public static void main(String[] args) {
        // Charge le contexte Spring (ton fichier XML ou config Java)
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        // Récupère ton bean service
        JourFerieService jourFerieService = context.getBean(JourFerieService.class);
        
        // Utilise ta méthode
        LocalDateTime dateRetour = jourFerieService.ajusterDateRetour(LocalDateTime.of(2025, 6, 26, 10, 0));
        System.out.println("Date de retour ajustée : " + dateRetour);
    }
}
