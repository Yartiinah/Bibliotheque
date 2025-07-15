package com.example.library.model; // Ou votre package d'entités

import jakarta.persistence.*;

@Entity
@Table(name = "\"user\"") // "user" est un mot-clé réservé en SQL, donc il doit être entre guillemets
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY pour SERIAL/BIGSERIAL en PostgreSQL
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String role; // Vous pourriez vouloir utiliser un enum ici aussi

    // Constructeurs
    public User() {
    }

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", role='" + role + '\'' +
               '}';
    }
}