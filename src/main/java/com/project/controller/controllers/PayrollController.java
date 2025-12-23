package com.project.controller.controllers;

import com.project.dto.PayrollCalculationRequest;
import com.project.entity.Payroll;
import com.project.entity.Personel;
import com.project.service.helpers.PayrollCalculator;
import com.project.service.interfaces.IPayrollService;
import com.project.service.interfaces.IPersonelService;
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

    @Autowired
    private IPersonelService personelService;

    private final PayrollCalculator payrollCalculator = new PayrollCalculator();
    
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

    /**
     * Modüler bordro oluşturma endpoint'i.
     * Frontend'den gelen parametreler ile bordro hesaplanır ve kaydedilir.
     */
    @PostMapping("/calculate")
    public ResponseEntity<?> calculatePayroll(@RequestBody PayrollCalculationRequest request) {
        try {
            if (request.getPersonelId() == null) {
                return ResponseEntity.badRequest().body("Personel seçilmedi.");
            }

            Personel personel = personelService.getByIdAsync(request.getPersonelId())
                    .join()
                    .orElse(null);

            if (personel == null) {
                return ResponseEntity.badRequest().body("Personel bulunamadı.");
            }

            int daysWorked = request.getDaysWorked() != null ? request.getDaysWorked() : 30;
            int overtime = request.getOvertime() != null ? request.getOvertime() : 0;

            Payroll payroll = payrollCalculator.calculate(
                    personel,
                    daysWorked,
                    overtime,
                    request.getPayrollDate(),
                    request.getDescription()
            );

            Payroll created = payrollService.addAsync(payroll).join();
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Bordro hesaplanırken hata oluştu: " + ex.getMessage());
        }
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

