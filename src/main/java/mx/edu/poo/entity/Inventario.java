package mx.edu.poo.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_juego")
    private Integer idJuego;

    @Column(name = "nombre_juego", length = 100, unique = true, nullable = false)
    private String nombreJuego;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "juego")
    private List<Prestamo> prestamos;

    // Constructores
    public Inventario() {}

    public Inventario(String nombreJuego, Integer cantidad) {
        this.nombreJuego = nombreJuego;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Integer getIdJuego() { return idJuego; }
    public void setIdJuego(Integer idJuego) { this.idJuego = idJuego; }

    public String getNombreJuego() { return nombreJuego; }
    public void setNombreJuego(String nombreJuego) { this.nombreJuego = nombreJuego; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public List<Prestamo> getPrestamos() { return prestamos; }
    public void setPrestamos(List<Prestamo> prestamos) { this.prestamos = prestamos; }
}