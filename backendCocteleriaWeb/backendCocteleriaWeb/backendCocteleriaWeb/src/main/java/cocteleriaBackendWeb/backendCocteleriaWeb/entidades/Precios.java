package cocteleriaBackendWeb.backendCocteleriaWeb.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "precios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Precios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_precio")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Cocteles.Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "id_tamaño")
    private Tamaños tamaños;

    private BigDecimal precio;

}