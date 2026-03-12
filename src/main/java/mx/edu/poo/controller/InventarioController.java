package mx.edu.poo.controller;

import mx.edu.poo.entity.Inventario;
import mx.edu.poo.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    @Autowired
    private InventarioRepository inventarioRepository;

    @PostMapping
    public Inventario crearJuego(@RequestBody Inventario juego) {
        return inventarioRepository.save(juego);
    }

    @GetMapping
    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> obtenerPorId(@PathVariable Integer id) {
        return inventarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventario> actualizarJuego(@PathVariable Integer id, @RequestBody Inventario juego) {
        return inventarioRepository.findById(id)
                .map(existente -> {
                    juego.setIdJuego(id);
                    return ResponseEntity.ok(inventarioRepository.save(juego));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJuego(@PathVariable Integer id) {
        return inventarioRepository.findById(id)
                .map(juego -> {
                    inventarioRepository.delete(juego);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/cantidad")
    public ResponseEntity<Inventario> actualizarCantidad(@PathVariable Integer id, @RequestParam Integer nuevaCantidad) {
        return inventarioRepository.findById(id)
                .map(juego -> {
                    juego.setCantidad(nuevaCantidad);
                    return ResponseEntity.ok(inventarioRepository.save(juego));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}