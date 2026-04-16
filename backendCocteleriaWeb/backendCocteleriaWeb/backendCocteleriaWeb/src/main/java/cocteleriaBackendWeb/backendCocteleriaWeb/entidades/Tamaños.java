package cocteleriaBackendWeb.backendCocteleriaWeb.entidades;


import jakarta.persistence.*;

@Entity
@Table(name = "tamaños")
public class Tamaños {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tamaño")
    private Integer id;

    private String nombre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}