package com.project.service.interfaces;

import com.project.entity.User;


public interface IUserService extends IService<User> {

 
    User findByUsername(String username);
}

