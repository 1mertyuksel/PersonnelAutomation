package com.project.controller.views;

import com.project.entity.User;
import com.project.service.interfaces.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Authentication Controller
 * Basit login/logout akışı (demo amaçlı, gerçek projede Spring Security tercih edilmeli)
 */
@Controller
public class AuthController {

    @Autowired
    private IUserService userService;

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
            HttpSession session,
            Model model) {

        User user = userService.findByUsername(username);
        if (user == null || user.getPassword() == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Kullanıcı adı veya şifre hatalı!");
            return "auth/login";
        }

        // Basit session bazlı login
        session.setAttribute("CURRENT_USER", user);
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}

