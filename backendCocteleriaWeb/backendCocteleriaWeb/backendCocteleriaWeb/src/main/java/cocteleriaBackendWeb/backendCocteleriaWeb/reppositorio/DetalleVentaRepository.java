package cocteleriaBackendWeb.backendCocteleriaWeb.reppositorio;

import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.DetalleVentas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVentas,Integer> {
}
