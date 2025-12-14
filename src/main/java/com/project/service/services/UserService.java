package com.project.service.services;

import com.project.entity.User;
import com.project.repository.IRepository;
import com.project.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User Entity için Service Implementation
 * Login ve kullanıcı işlemleri için business logic
 */
@Service
public class UserService extends com.project.service.services.Service<User> implements IUserService {
    
    @Autowired
    public UserService(IRepository<User> repository) {
        super(repository);
    }
    
    // User'a özel metodlar buraya eklenebilir
    // Örneğin: findByUsername, authenticate, etc.
}

