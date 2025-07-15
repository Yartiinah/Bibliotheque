package repository;

import model.Adherent;
import model.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdherentRepository extends JpaRepository<Adherent, Integer> {
    Adherent findByEmail(String email);
}