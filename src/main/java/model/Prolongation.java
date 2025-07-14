package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prolongation")
public class Prolongation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pret_id", nullable = false)
    private Pret pret;

    @Column(name = "date_demande", nullable = false)
    private LocalDateTime dateDemande;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutProlongation statut;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public LocalDateTime getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }

    public StatutProlongation getStatut() {
        return statut;
    }

    public void setStatut(StatutProlongation statut) {
        this.statut = statut;
    }
}

enum StatutProlongation {
    EN_ATTENTE, ACCEPTEE, REFUSEE
}