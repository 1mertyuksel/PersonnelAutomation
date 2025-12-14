package com.project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass // Bu sınıf veritabanında tablo olmayacak ama miras verilecek
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private Long id;

    private LocalDateTime createdDate = LocalDateTime.now();

    // Getter ve Setter'ları buraya ekle (veya Lombok @Data kullan)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}