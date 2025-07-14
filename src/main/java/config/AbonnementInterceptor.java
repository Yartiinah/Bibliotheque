package config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utilisateur;
import model.Adherent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Component;
import service.AdherentService;

@Component
public class AbonnementInterceptor implements HandlerInterceptor {
    @Autowired
    private AdherentService adherentService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object userObj = session.getAttribute("user");
            if (userObj != null && userObj instanceof Utilisateur user) {
                if ("ADHERENT".equalsIgnoreCase(user.getRole()) && user.getAdherent() != null) {
                    Adherent adherent = user.getAdherent();
                    boolean valide = adherentService.isInscriptionValide(adherent);
                    // On laisse passer la navigation, mais on met l'info dans la session
                    session.setAttribute("inscriptionValide", valide);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Rien à faire ici
    }
}
