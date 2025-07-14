package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "membre_id")
    private Membre membre;

    private LocalDateTime dateInscription;
    private double montantCotisation;

    @Enumerated(EnumType.STRING)
    private StatutInscription statut;

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Membre getMembre() { return membre; }
    public void setMembre(Membre membre) { this.membre = membre; }
    public LocalDateTime getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDateTime dateInscription) { this.dateInscription = dateInscription; }
    public double getMontantCotisation() { return montantCotisation; }
    public void setMontantCotisation(double montantCotisation) { this.montantCotisation = montantCotisation; }
    public StatutInscription getStatut() { return statut; }
    public void setStatut(StatutInscription statut) { this.statut = statut; }
}