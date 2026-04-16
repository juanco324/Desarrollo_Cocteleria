package cocteleriaBackendWeb.backendCocteleriaWeb.reppositorio;

import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.Cocteles;
import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.Precios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrecioRepository extends JpaRepository<Precios, Long> {

    List<Precios> findByTipo(Cocteles.Tipo tipo);
}
