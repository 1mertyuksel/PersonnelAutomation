package com.project.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inventory_items")
public class InventoryItem extends BaseEntity {

    private String productName; // "Macbook Pro", "Şirket Aracı"
    private String serialNumber; // "S/N: 123456"
    
    private LocalDate assignedDate; // Ne zaman verildi?

    // Bu eşya kime zimmetli?
    @ManyToOne
    @JoinColumn(name = "personnel_id")
    private Personel personel;

    // Getter & Setter
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public LocalDate getAssignedDate() { return assignedDate; }
    public void setAssignedDate(LocalDate assignedDate) { this.assignedDate = assignedDate; }
    public Personel getPersonel() { return personel; }
    public void setPersonel(Personel personel) { this.personel = personel; }
}