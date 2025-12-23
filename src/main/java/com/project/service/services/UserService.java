package com.project.service.services;

import com.project.entity.User;
import com.project.repository.IRepository;
import com.project.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService extends com.project.service.services.Service<User> implements IUserService {
    
    @Autowired
    public UserService(IRepository<User> repository) {
        super(repository);
    }

    @Override
    public User findByUsername(String username) {
        return this.findAll().stream()
                .filter(u -> u.getUsername() != null && u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }
}

