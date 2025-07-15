package repository;

import model.RoleUtilisateur;
import model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    List<Utilisateur> findByRole(RoleUtilisateur role);
    Utilisateur findByUsername(String username);
}