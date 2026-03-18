package mx.edu.poo.controller;

import mx.edu.poo.entity.Registro;
import mx.edu.poo.repository.RegistroRepository;
import mx.edu.poo.repository.AlumnoRepository;
import mx.edu.poo.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/registro")
@CrossOrigin(origins = "*")
public class RegistroController {

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @PostMapping
    public ResponseEntity<?> crearRegistro(@RequestBody Registro registro) {
        if (!alumnoRepository.existsById(registro.getAlumno().getMatricula())) {
            return ResponseEntity.badRequest().body("Alumno no existe");
        }
        if (!actividadRepository.existsById(registro.getActividad().getIdActividad())) {
            return ResponseEntity.badRequest().body("Actividad no existe");
        }
        return ResponseEntity.ok(registroRepository.save(registro));
    }

    @GetMapping
    public List<Registro> obtenerTodos() {
        return registroRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registro> obtenerPorId(@PathVariable Integer id) {
        return registroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Registro> actualizarRegistro(@PathVariable Integer id, @RequestBody Registro registro) {
        return registroRepository.findById(id)
                .map(existente -> {
                    registro.setIdRegistro(id);
                    return ResponseEntity.ok(registroRepository.save(registro));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Integer id) {
        return registroRepository.findById(id)
                .map(registro -> {
                    registroRepository.delete(registro);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/alumno/{matricula}")
    public List<Registro> obtenerPorAlumno(@PathVariable Integer matricula) {
        return registroRepository.findByAlumnoMatricula(matricula);
    }

    @GetMapping("/fecha/{fecha}")
    public List<Registro> obtenerPorFecha(@PathVariable String fecha) {
        LocalDate date = LocalDate.parse(fecha);
        return registroRepository.findByFecha(date);
    }
}