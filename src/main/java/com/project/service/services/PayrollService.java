package com.project.service.services;

import com.project.entity.Payroll;
import com.project.repository.IRepository;
import com.project.service.interfaces.IPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PayrollService extends com.project.service.services.Service<Payroll> implements IPayrollService {
    
    @Autowired
    public PayrollService(IRepository<Payroll> repository) {
        super(repository);
    }
    
   
}

