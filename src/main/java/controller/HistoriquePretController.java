package controller;

import model.HistoriquePret;
import service.HistoriquePretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HistoriquePretController {
    @Autowired
    private HistoriquePretService historiquePretService;

    @GetMapping("/historique-prets")
    public String getAllHistoriquePrets(Model model) {
        List<HistoriquePret> historiques = historiquePretService.getAll();
        model.addAttribute("historiques", historiques);
        return "historiquePrets";
    }

    @GetMapping("/historique-pret")
    public String getHistoriqueByPret(@RequestParam("pretId") Integer pretId, Model model) {
        List<HistoriquePret> historiques = historiquePretService.getByPretId(pretId);
        model.addAttribute("historiques", historiques);
        return "historiquePrets";
    }
}
