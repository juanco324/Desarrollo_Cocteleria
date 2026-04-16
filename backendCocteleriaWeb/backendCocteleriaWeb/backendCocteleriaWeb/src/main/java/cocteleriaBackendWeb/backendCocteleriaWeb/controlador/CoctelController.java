package cocteleriaBackendWeb.backendCocteleriaWeb.controlador;


import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.Cocteles;
import cocteleriaBackendWeb.backendCocteleriaWeb.servicio.CoctelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cocteles")
@CrossOrigin("*")
public class CoctelController {

    private final CoctelService service;

    public CoctelController(CoctelService service) {
        this.service = service;
    }

    // 🔍 VER TODOS LOS CÓCTELES
    @GetMapping
    public List<Cocteles> verProductos() {
        return service.listar();
    }

    // ➕ AGREGAR CÓCTEL
    @PostMapping
    public Cocteles guardarProductos(@RequestBody Cocteles coctel) {
        return service.guardar(coctel);
    }
}
