package mx.edu.poo.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Integer idActividad;

    @Column(name = "nombre_actividad", length = 100, unique = true, nullable = false)
    private String nombreActividad;

    @JsonIgnore
    @OneToMany(mappedBy = "actividad")
    private List<Registro> registros;

    // Constructores
    public Actividad() {}

    public Actividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    // Getters y Setters
    public Integer getIdActividad() { return idActividad; }
    public void setIdActividad(Integer idActividad) { this.idActividad = idActividad; }

    public String getNombreActividad() { return nombreActividad; }
    public void setNombreActividad(String nombreActividad) { this.nombreActividad = nombreActividad; }

    public List<Registro> getRegistros() { return registros; }
    public void setRegistros(List<Registro> registros) { this.registros = registros; }
}