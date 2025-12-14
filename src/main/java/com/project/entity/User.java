package com.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true) // Aynı kullanıcı adı 2 kez alınamasın
    private String username;
    
    private String password; // Gerçek hayatta şifrelenir ama projede düz tutabilirsin.
    
    private String role; // "ADMIN", "IK_PERSONEL" vs.

    // Getter & Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}