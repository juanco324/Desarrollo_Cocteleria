package cocteleriaBackendWeb.backendCocteleriaWeb.entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tamaños")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tamaños {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tamaño")
    private Integer id;

    private String nombre;

    @OneToMany(mappedBy = "tamaños")
    private List<DetalleVentas> detalleVentas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}