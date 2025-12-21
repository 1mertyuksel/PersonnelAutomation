package com.project.controller.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Authentication Controller
 * Login işlemleri için
 */
@Controller
public class AuthController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
        
        // Basit authentication (production'da Spring Security kullanılmalı)
        if ("admin".equals(username) && "admin".equals(password)) {
            return "redirect:/dashboard";
        }
        
        model.addAttribute("error", "Hatalı şifre!");
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}

