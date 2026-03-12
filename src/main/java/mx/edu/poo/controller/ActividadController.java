package mx.edu.poo.controller;

import mx.edu.poo.entity.Actividad;
import mx.edu.poo.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/actividades")
@CrossOrigin(origins = "*")
public class ActividadController {

    @Autowired
    private ActividadRepository actividadRepository;

    @PostMapping
    public Actividad crearActividad(@RequestBody Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    @GetMapping
    public List<Actividad> obtenerTodas() {
        return actividadRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actividad> obtenerPorId(@PathVariable Integer id) {
        return actividadRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actividad> actualizarActividad(@PathVariable Integer id, @RequestBody Actividad actividad) {
        return actividadRepository.findById(id)
                .map(existente -> {
                    actividad.setIdActividad(id);
                    return ResponseEntity.ok(actividadRepository.save(actividad));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Integer id) {
        return actividadRepository.findById(id)
                .map(actividad -> {
                    actividadRepository.delete(actividad);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}