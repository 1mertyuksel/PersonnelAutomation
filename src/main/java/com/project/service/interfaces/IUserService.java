package com.project.service.interfaces;

import com.project.entity.User;

/**
 * User Entity için Service Interface
 * Login ve kullanıcı işlemleri için business logic interface
 */
public interface IUserService extends IService<User> {

    /**
     * Kullanıcı adından kullanıcıyı bulur (yoksa null döner).
     */
    User findByUsername(String username);
}

