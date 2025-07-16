package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiement")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_adherent", nullable = false)
    private Adherent adherent;

    @Column(name = "date_paiement", nullable = false)
    private LocalDateTime datePaiement;

    @Column(nullable = false)
    private double montant;

    @Column(name = "mode_paiement", nullable = false)
    private String modePaiement;

    @Column(nullable = false)
    private String type;

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }
    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
    public String getModePaiement() { return modePaiement; }
    public void setModePaiement(String modePaiement) { this.modePaiement = modePaiement; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
