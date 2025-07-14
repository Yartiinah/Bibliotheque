package repository;

import model.HistoriquePret;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoriquePretRepository extends JpaRepository<HistoriquePret, Integer> {
    List<HistoriquePret> findByPretId(Integer pretId);
}
