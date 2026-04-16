package cocteleriaBackendWeb.backendCocteleriaWeb.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "cocteles")
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

    // getters y setters


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
