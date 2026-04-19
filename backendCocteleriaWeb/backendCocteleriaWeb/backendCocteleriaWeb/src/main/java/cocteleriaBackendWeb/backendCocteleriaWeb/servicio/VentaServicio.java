package cocteleriaBackendWeb.backendCocteleriaWeb.servicio;

import cocteleriaBackendWeb.backendCocteleriaWeb.entidades.*;
import cocteleriaBackendWeb.backendCocteleriaWeb.reppositorio.PrecioRepository;
import cocteleriaBackendWeb.backendCocteleriaWeb.reppositorio.UsuarioRepository;
import cocteleriaBackendWeb.backendCocteleriaWeb.reppositorio.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VentaServicio {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private PrecioRepository precioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Venta crearVenta(Venta venta){

        int total = 0;



        for(DetalleVentas dt : venta.getDetalleVentas()){

            Cocteles.Tipo tipo = dt.getId_cocteles().getTipo();
            Tamaños tamano = dt.getId_tamaños();

            Precios p = precioRepository
                    .findByTipoAndTamanos(tipo, tamano)
                    .orElseThrow(() -> new RuntimeException("Precio no definido"));

            int precioUnidad = p.getPrecio().intValue();
            int subtotal = precioUnidad * dt.getCantidad();

            dt.setPrecio_unitario(precioUnidad);
            dt.setSubtotal(subtotal);
            dt.setId_venta(venta);

            total += subtotal;
        }
        // 🔥 obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        venta.setUsuario(usuario);

        venta.setLocalDateTime(LocalDateTime.now());
        venta.setTotal(total);

        return ventaRepository.save(venta);
    }
}