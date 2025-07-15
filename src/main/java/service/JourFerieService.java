package service;

import model.JourFerie;
import repository.JourFerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class JourFerieService {

    @Autowired
    private JourFerieRepository jourFerieRepository;

    public boolean estWeekend(LocalDate date) {
        DayOfWeek jour = date.getDayOfWeek();
        return jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY;
    }

    public boolean estJourFerie(LocalDate date) {
        return jourFerieRepository.existsByDateFerie(date);
    }

    public boolean estJourNonOuvre(LocalDate date) {
        boolean isWeekend = date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        boolean isFerie = jourFerieRepository.existsByDateFerie(date);
        System.out.println("Date vérifiée : " + date + ", weekend: " + isWeekend + ", férié: " + isFerie);
        return isWeekend || isFerie;
    }

    public LocalDateTime ajusterDateRetour(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        while (estJourNonOuvre(date)) {
            date = date.plusDays(1);
        }
        // On garde l'heure de départ (par ex. 17:36:08) mais avec la nouvelle date
        return LocalDateTime.of(date, dateTime.toLocalTime());
    }

}
