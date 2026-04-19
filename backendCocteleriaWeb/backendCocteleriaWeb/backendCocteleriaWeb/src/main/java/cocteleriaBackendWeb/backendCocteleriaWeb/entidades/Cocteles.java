package cocteleriaBackendWeb.backendCocteleriaWeb.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cocteles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cocteles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coctel")
    private Integer id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    public enum Tipo {
        CON_LICOR,
        SIN_LICOR
    }

    @OneToMany(mappedBy = "cocteles")
    private List<DetalleVentas> detalleVentas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }
}
