package com.project.controller.controllers;

import com.project.entity.Personel;
import com.project.service.interfaces.IPersonelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Personel Entity için Controller
 * REST API endpoint'leri
 */
@RestController
@RequestMapping("/api/personel")
public class PersonelController {
    
    @Autowired
    private IPersonelService personelService;
    
    @GetMapping
    public ResponseEntity<List<Personel>> getAll() {
        List<Personel> personeller = personelService.findAll();
        return ResponseEntity.ok(personeller);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Personel>> getById(@PathVariable Long id) {
        Optional<Personel> personel = personelService.getByIdAsync(id).join();
        if (personel.isPresent()) {
            return ResponseEntity.ok(personel);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Personel> create(@RequestBody Personel personel) {
        Personel created = personelService.addAsync(personel).join();
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Personel> update(@PathVariable Long id, @RequestBody Personel personel) {
        personel.setId(id);
        Personel updated = personelService.updateAsync(personel).join();
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personelService.removeAsync(id).join();
        return ResponseEntity.noContent().build();
    }
}

