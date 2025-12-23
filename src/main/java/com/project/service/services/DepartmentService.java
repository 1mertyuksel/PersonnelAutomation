package com.project.service.services;

import com.project.entity.Department;
import com.project.repository.IRepository;
import com.project.service.interfaces.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DepartmentService extends com.project.service.services.Service<Department> implements IDepartmentService {

    @Autowired
    public DepartmentService(IRepository<Department> repository) {
        super(repository);
    }
}


