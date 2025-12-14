package com.project.controller.controllers;

import com.project.entity.InventoryItem;
import com.project.service.interfaces.IInventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * InventoryItem Entity için Controller
 * Zimmet (Envanter) işlemleri için REST API endpoint'leri
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryItemController {
    
    @Autowired
    private IInventoryItemService inventoryItemService;
    
    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAll() {
        List<InventoryItem> items = inventoryItemService.findAll();
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<InventoryItem>> getById(@PathVariable Long id) {
        Optional<InventoryItem> item = inventoryItemService.getByIdAsync(id).join();
        if (item.isPresent()) {
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<InventoryItem> create(@RequestBody InventoryItem inventoryItem) {
        InventoryItem created = inventoryItemService.addAsync(inventoryItem).join();
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> update(@PathVariable Long id, @RequestBody InventoryItem inventoryItem) {
        inventoryItem.setId(id);
        InventoryItem updated = inventoryItemService.updateAsync(inventoryItem).join();
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryItemService.removeAsync(id).join();
        return ResponseEntity.noContent().build();
    }
}

