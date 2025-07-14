package config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import service.MembreService;

import java.util.Map;

@Component
public class AbonnementInterceptor implements HandlerInterceptor {

    @Autowired
    private MembreService membreService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Map<String, Object> membreConnecte = (Map<String, Object>) session.getAttribute("membreConnecte");
            if (membreConnecte != null) {
                Integer membreId = (Integer) membreConnecte.get("id");
                boolean inscriptionValide = membreService.isInscriptionValide(membreId);
                session.setAttribute("inscriptionValide", inscriptionValide);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Rien à faire ici
    }
}