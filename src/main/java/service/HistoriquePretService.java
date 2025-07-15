package service;

import model.HistoriquePret;
import repository.HistoriquePretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoriquePretService {
    @Autowired
    private HistoriquePretRepository historiquePretRepository;

    public void save(HistoriquePret historiquePret) {
        historiquePretRepository.save(historiquePret);
    }

    public List<HistoriquePret> getByPretId(Integer pretId) {
        return historiquePretRepository.findByPretId(pretId);
    }

    public List<HistoriquePret> getAll() {
        return historiquePretRepository.findAll();
    }
}
