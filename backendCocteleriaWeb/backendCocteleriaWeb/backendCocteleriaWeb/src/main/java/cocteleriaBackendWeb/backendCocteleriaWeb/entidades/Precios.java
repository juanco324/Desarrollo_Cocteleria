package cocteleriaBackendWeb.backendCocteleriaWeb.entidades;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "precios")
public class Precios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_precio")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Cocteles.Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "id_tamaño")
    private Tamaños tamano;

    private BigDecimal precio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cocteles.Tipo getTipo() { return tipo; }
    public void setTipo(Cocteles.Tipo tipo) { this.tipo = tipo; }

    public Tamaños getTamano() { return tamano; }
    public void setTamano(Tamaños tamano) { this.tamano = tamano; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
}