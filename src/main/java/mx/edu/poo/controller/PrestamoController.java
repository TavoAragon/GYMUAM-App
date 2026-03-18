package mx.edu.poo.controller;

import mx.edu.poo.entity.Prestamo;
import mx.edu.poo.repository.PrestamoRepository;
import mx.edu.poo.repository.AlumnoRepository;
import mx.edu.poo.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestBody Prestamo prestamo) {
        if (!alumnoRepository.existsById(prestamo.getAlumno().getMatricula())) {
            return ResponseEntity.badRequest().body("El alumno no existe");
        }
        if (!inventarioRepository.existsById(prestamo.getJuego().getIdJuego())) {
            return ResponseEntity.badRequest().body("El juego no existe");
        }
        prestamo.setFechaPrestamo(LocalDate.now());
        return ResponseEntity.ok(prestamoRepository.save(prestamo));
    }

    @GetMapping
    public List<Prestamo> obtenerTodos() {
        return prestamoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPorId(@PathVariable Integer id) {
        return prestamoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> devolverJuego(@PathVariable Integer id) {
        return prestamoRepository.findById(id)
                .map(prestamo -> {
                    prestamo.setFechaDevolucion(LocalDate.now());
                    return ResponseEntity.ok(prestamoRepository.save(prestamo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/alumno/{matricula}")
    public List<Prestamo> obtenerPorAlumno(@PathVariable Integer matricula) {
        return prestamoRepository.findByAlumnoMatricula(matricula);
    }

    @GetMapping("/activos")
    public List<Prestamo> obtenerActivos() {
        return prestamoRepository.findByFechaDevolucionIsNull();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable Integer id) {
        return prestamoRepository.findById(id)
                .map(prestamo -> {
                    prestamoRepository.delete(prestamo);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}