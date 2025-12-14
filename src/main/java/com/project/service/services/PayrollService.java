package com.project.service.services;

import com.project.entity.Payroll;
import com.project.repository.IRepository;
import com.project.service.interfaces.IPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Payroll Entity için Service Implementation
 * Maaş bordrosu ve finansal hesaplamalar için business logic
 */
@Service
public class PayrollService extends com.project.service.services.Service<Payroll> implements IPayrollService {
    
    @Autowired
    public PayrollService(IRepository<Payroll> repository) {
        super(repository);
    }
    
    // Payroll'a özel metodlar buraya eklenebilir
    // Örneğin: findByPersonelId, calculateSalary, generatePayroll, etc.
}

