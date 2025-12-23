package com.project.config;

import com.project.entity.User;
import com.project.service.interfaces.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Uygulama ilk açıldığında basit bir admin kullanıcısı oluşturur (eğer yoksa).
 * Kullanıcı adı: admin, Şifre: admin
 */
@Component
public class UserSeeder implements CommandLineRunner {

    private final IUserService userService;

    public UserSeeder(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        // Eğer hiç kullanıcı yoksa basit bir admin oluştur
        if (!userService.findAll().isEmpty()) {
            return;
        }

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin"); // Demo amaçlı; prod'da hash kullanılmalı
        admin.setRole("ADMIN");

        userService.addAsync(admin).join();
    }
}


