package com.utez.EquipoValeriaLuis.Contactos.controller;

import com.utez.EquipoValeriaLuis.Contactos.model.Contacto;
import com.utez.EquipoValeriaLuis.Contactos.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@CrossOrigin(origins = "*")
public class ContactoController {

    @Autowired
    private ContactoRepository repository;

    @GetMapping
    public List<Contacto> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Contacto contacto) {
        if (contacto.getNombre() == null || contacto.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body("Nombre requerido");
        }
        if (contacto.getTelefono() == null || contacto.getTelefono().isBlank()) {
            return ResponseEntity.badRequest().body("Teléfono requerido");
        }
        Contacto saved = repository.save(contacto);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.badRequest().body("Contacto no encontrado");
        }
        repository.deleteById(id);
        return ResponseEntity.ok("Contacto eliminado");
    }
}

