package model;

import jakarta.persistence.*;

@Entity
public class Exemplaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String reference;

    @ManyToOne
    @JoinColumn(name = "id_livre")
    private Livre livre;

    @Column(nullable = false)
    private String statut; // "disponible", "emprunte", "reserve"

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}