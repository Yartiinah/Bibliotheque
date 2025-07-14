package repository;

import model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByUsernameAndPassword(String username, String password);
    Optional<Utilisateur> findByUsername(String username);
}