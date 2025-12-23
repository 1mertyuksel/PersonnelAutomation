package com.project.service.services;

import com.project.entity.Personel;
import com.project.repository.IRepository;
import com.project.service.interfaces.IPersonelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonelService extends com.project.service.services.Service<Personel> implements IPersonelService {
    
    @Autowired
    public PersonelService(IRepository<Personel> repository) {
        super(repository);
    }
    
    
}

