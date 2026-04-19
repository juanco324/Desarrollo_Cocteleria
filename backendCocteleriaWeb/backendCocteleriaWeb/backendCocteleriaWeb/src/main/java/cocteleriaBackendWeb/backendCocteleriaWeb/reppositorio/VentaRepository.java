package cocteleriaBackendWeb.backendCocteleriaWeb.reppositorio;

import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
}
