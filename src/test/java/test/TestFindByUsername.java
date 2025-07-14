package test;

import model.Utilisateur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import repository.UtilisateurRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TestFindByUsername {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Test
    public void testFindByUsername() {
        Utilisateur user = utilisateurRepository.findByUsername("test").orElse(null);
        if (user != null) {
            System.out.println("Trouvé : " + user.getUsername() + ", mot de passe : " + user.getPassword());
        } else {
            System.out.println("Non trouvé");
        }
        assertNotNull(user);
    }
}