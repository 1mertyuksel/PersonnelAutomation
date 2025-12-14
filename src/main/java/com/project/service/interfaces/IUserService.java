package com.project.service.interfaces;

import com.project.entity.User;

/**
 * User Entity için Service Interface
 * Login ve kullanıcı işlemleri için business logic interface
 */
public interface IUserService extends IService<User> {
    
    // User'a özel metodlar buraya eklenebilir
    // Örneğin: findByUsername, authenticate, etc.
}

