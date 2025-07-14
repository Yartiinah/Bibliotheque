package service;

import model.RoleUtilisateur;
import model.Utilisateur;
import repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Utilisateur authenticate(String username, String password) {
        Utilisateur utilisateur = utilisateurRepository.findByUsernameAndPassword(username, password);
        if (utilisateur != null && utilisateur.getRole() == RoleUtilisateur.BIBLIOTHECAIRE) {
            return utilisateur;
        }
        return null;
    }
}