package cocteleriaBackendWeb.backendCocteleriaWeb.reppositorio;

import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.Cocteles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoctelRepository extends JpaRepository<Cocteles, Integer> {
}
