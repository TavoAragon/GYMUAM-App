package mx.edu.poo.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @Column(name = "matricula")
    private Integer matricula;

    @Column(name = "nombre_completo", length = 100)
    private String nombreCompleto;

    @Column(name = "carrera", length = 100)
    private String carrera;

    @Column(name = "password", length = 255)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "alumno")
    private List<Registro> registros;

    @JsonIgnore
    @OneToMany(mappedBy = "alumno")
    private List<Prestamo> prestamos;

    // Constructores
    public Alumno() {}

    public Alumno(Integer matricula, String nombreCompleto, String carrera, String password) {
        this.matricula = matricula;
        this.nombreCompleto = nombreCompleto;
        this.carrera = carrera;
        this.password = password;
    }

    // Getters y Setters
    public Integer getMatricula() { return matricula; }
    public void setMatricula(Integer matricula) { this.matricula = matricula; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Registro> getRegistros() { return registros; }
    public void setRegistros(List<Registro> registros) { this.registros = registros; }

    public List<Prestamo> getPrestamos() { return prestamos; }
    public void setPrestamos(List<Prestamo> prestamos) { this.prestamos = prestamos; }
}