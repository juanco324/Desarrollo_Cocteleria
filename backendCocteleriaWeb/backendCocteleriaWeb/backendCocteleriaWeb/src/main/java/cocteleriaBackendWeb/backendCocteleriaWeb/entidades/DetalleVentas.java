package cocteleriaBackendWeb.backendCocteleriaWeb.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_detalle;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta id_venta;

    @ManyToOne
    @JoinColumn(name = "id_coctel")
    private Cocteles id_cocteles;

    @ManyToOne
    @JoinColumn(name = "id_tamaño")
    private Tamaños id_tamaños;

    private Integer cantidad;
    private Integer precio_unitario;
    private Integer subtotal;

}
