package mx.edu.poo.controller;

import mx.edu.poo.entity.Alumno;
import mx.edu.poo.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@CrossOrigin(origins = "*")
public class AlumnoController {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @PostMapping
    public Alumno crearAlumno(@RequestBody Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    @GetMapping
    public List<Alumno> obtenerTodos() {
        return alumnoRepository.findAll();
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Alumno> obtenerPorMatricula(@PathVariable Integer matricula) {
        return alumnoRepository.findById(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<Alumno> actualizarAlumno(@PathVariable Integer matricula, @RequestBody Alumno alumno) {
        return alumnoRepository.findById(matricula)
                .map(existente -> {
                    alumno.setMatricula(matricula);
                    return ResponseEntity.ok(alumnoRepository.save(alumno));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Integer matricula) {
        return alumnoRepository.findById(matricula)
                .map(alumno -> {
                    alumnoRepository.delete(alumno);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}