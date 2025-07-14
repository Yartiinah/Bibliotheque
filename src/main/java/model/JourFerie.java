package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jourferie")
public class JourFerie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_ferie", unique = true, nullable = false)
    private LocalDateTime dateFerie;

    private String description;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateFerie() {
        return dateFerie;
    }

    public void setDateFerie(LocalDateTime dateFerie) {
        this.dateFerie = dateFerie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}