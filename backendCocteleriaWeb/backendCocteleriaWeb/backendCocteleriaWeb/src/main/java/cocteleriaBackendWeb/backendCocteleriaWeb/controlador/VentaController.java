package cocteleriaBackendWeb.backendCocteleriaWeb.controlador;

import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.Venta;
import cocteleriaBackendWeb.backendCocteleriaWeb.servicio.VentaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaServicio ventaServicio;

    // 🔥 CREAR VENTA
    @PostMapping
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta){

        Venta nuevaVenta = ventaServicio.crearVenta(venta);

        return ResponseEntity.ok(nuevaVenta);
    }
}