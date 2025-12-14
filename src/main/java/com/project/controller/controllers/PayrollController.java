package com.project.controller.controllers;

import com.project.entity.Payroll;
import com.project.service.interfaces.IPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Payroll Entity için Controller
 * Maaş bordrosu işlemleri için REST API endpoint'leri
 */
@RestController
@RequestMapping("/api/payroll")
public class PayrollController {
    
    @Autowired
    private IPayrollService payrollService;
    
    @GetMapping
    public ResponseEntity<List<Payroll>> getAll() {
        List<Payroll> payrolls = payrollService.findAll();
        return ResponseEntity.ok(payrolls);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Payroll>> getById(@PathVariable Long id) {
        Optional<Payroll> payroll = payrollService.getByIdAsync(id).join();
        if (payroll.isPresent()) {
            return ResponseEntity.ok(payroll);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Payroll> create(@RequestBody Payroll payroll) {
        Payroll created = payrollService.addAsync(payroll).join();
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Payroll> update(@PathVariable Long id, @RequestBody Payroll payroll) {
        payroll.setId(id);
        Payroll updated = payrollService.updateAsync(payroll).join();
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        payrollService.removeAsync(id).join();
        return ResponseEntity.noContent().build();
    }
}

