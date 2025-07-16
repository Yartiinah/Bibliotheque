package model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class JourFerie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_ferie", nullable = false, unique = true)
    private LocalDate dateFerie;

    @Column(length = 100)
    private String description;

    // Getters et setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDateFerie() {
        return dateFerie;
    }

    public void setDateFerie(LocalDate dateFerie) {
        this.dateFerie = dateFerie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
