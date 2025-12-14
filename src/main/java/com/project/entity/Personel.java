package com.project.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "personnel")
public class Personel extends BaseEntity {

    private String firstName;
    private String lastName;
    
    private String department; // "Yazılım", "Muhasebe" (Enum yapmaya gerek yok, String yeterli)
    
    private String photoUrl; // Resmin kendisini değil, dosya yolunu tutarız.

    private LocalDate hireDate; // İşe giriş (Kıdem hesabı için)

    // --- MAAŞ HESAPLAMA VERİLERİ ---
    private BigDecimal salary; // Güncel Net Maaş

    // --- İZİN SİSTEMİ VERİLERİ ---
    private int annualLeaveQuota = 14; // Yıllık izin hakkı (Varsayılan 14)
    private int usedLeaveDays = 0;     // Kullanılan gün sayısı

    // İLİŞKİLER
    
    // 1 Personelin birden çok zimmetli eşyası olabilir.
    @OneToMany(mappedBy = "personel", cascade = CascadeType.ALL)
    private List<InventoryItem> inventoryItems;

    // 1 Personelin birden çok izin talebi olabilir.
    @OneToMany(mappedBy = "personel", cascade = CascadeType.ALL)
    private List<LeaveRequest> leaveRequests;

    // Getter & Setter
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public int getAnnualLeaveQuota() { return annualLeaveQuota; }
    public void setAnnualLeaveQuota(int annualLeaveQuota) { this.annualLeaveQuota = annualLeaveQuota; }
    public int getUsedLeaveDays() { return usedLeaveDays; }
    public void setUsedLeaveDays(int usedLeaveDays) { this.usedLeaveDays = usedLeaveDays; }
    public List<InventoryItem> getInventoryItems() { return inventoryItems; }
    public void setInventoryItems(List<InventoryItem> inventoryItems) { this.inventoryItems = inventoryItems; }
    public List<LeaveRequest> getLeaveRequests() { return leaveRequests; }
    public void setLeaveRequests(List<LeaveRequest> leaveRequests) { this.leaveRequests = leaveRequests; }
}