package mx.edu.poo.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer idPrestamo;

    @ManyToOne
    @JoinColumn(name = "matricula", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "id_juego", nullable = false)
    private Inventario juego;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    // Constructores
    public Prestamo() {}

    public Prestamo(Alumno alumno, Inventario juego, LocalDate fechaPrestamo) {
        this.alumno = alumno;
        this.juego = juego;
        this.fechaPrestamo = fechaPrestamo;
    }

    // Getters y Setters
    public Integer getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(Integer idPrestamo) { this.idPrestamo = idPrestamo; }

    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }

    public Inventario getJuego() { return juego; }
    public void setJuego(Inventario juego) { this.juego = juego; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
}