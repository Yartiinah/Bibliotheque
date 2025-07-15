package test;

import model.Utilisateur;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.UtilisateurRepository;

public class TestFindByUsername {
    @Test
    public void testFindByUsername() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:WEB-INF/app-context.xml");
        UtilisateurRepository utilisateurRepository = context.getBean(UtilisateurRepository.class);
        Utilisateur user = utilisateurRepository.findByUsername("test").orElse(null);
        if (user != null) {
            System.out.println("Trouvé : " + user.getUsername() + ", mot de passe : " + user.getPassword());
        } else {
            System.out.println("Non trouvé");
        }
        Assert.assertNotNull(user);
    }
}
