package cocteleriaBackendWeb.backendCocteleriaWeb.reppositorio;

import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.Cocteles;
import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.Precios;
import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.Tamaños;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrecioRepository extends JpaRepository<Precios, Long> {

    Optional<Precios> findByTipoAndTamanos(Cocteles.Tipo tipo, Tamaños tamaños);
}
