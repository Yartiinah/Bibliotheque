package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "penalite")
public class Penalite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_adherent")
    private Adherent adherent;

    @ManyToOne
    @JoinColumn(name = "id_pret")
    private Pret pret;

    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;

    @Column(nullable = false)
    private boolean reglee = false;


    // Getters et setters...
    public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}

public Adherent getAdherent() {
    return adherent;
}

public void setAdherent(Adherent adherent) {
    this.adherent = adherent;
}

public Pret getPret() {
    return pret;
}

public void setPret(Pret pret) {
    this.pret = pret;
}

public LocalDateTime getDateDebut() {
    return dateDebut;
}

public void setDateDebut(LocalDateTime dateDebut) {
    this.dateDebut = dateDebut;
}

public LocalDateTime getDateFin() {
    return dateFin;
}

public void setDateFin(LocalDateTime dateFin) {
    this.dateFin = dateFin;
}

public boolean isReglee() {
    return reglee;
}

public void setReglee(boolean reglee) {
    this.reglee = reglee;
}

}