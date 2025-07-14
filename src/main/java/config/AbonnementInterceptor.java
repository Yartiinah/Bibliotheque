package config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import service.MembreService;

import java.util.Map;

@Component
public class AbonnementInterceptor implements HandlerInterceptor {

    @Autowired
    private MembreService membreService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> membre = (Map<String, Object>) request.getSession().getAttribute("membreConnecte");
        if (membre == null) {
            response.sendRedirect("/membre/login-form");
            return false;
        }
        boolean valide = membreService.isInscriptionValide((Integer) membre.get("id"));
        request.getSession().setAttribute("inscriptionValide", valide);
        return true;
    }
}