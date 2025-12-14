package com.project.service.interfaces;

import com.project.entity.Payroll;

/**
 * Payroll Entity için Service Interface
 * Maaş bordrosu ve finansal hesaplamalar için business logic interface
 */
public interface IPayrollService extends IService<Payroll> {
    
    // Payroll'a özel metodlar buraya eklenebilir
    // Örneğin: findByPersonelId, calculateSalary, generatePayroll, etc.
}

