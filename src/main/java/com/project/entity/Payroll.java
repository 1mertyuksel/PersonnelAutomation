package com.project.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payrolls")
public class Payroll extends BaseEntity {

    // Hangi ay ve yılın maaşı? (Örn: 2025-12-01 tarihli kayıt Aralık maaşıdır)
    private LocalDate payrollDate; 

    private BigDecimal grossSalary; // Brüt
    private BigDecimal netSalary;   // Ele geçen net (Hesaplamadan sonra buraya yazılacak)
    private BigDecimal deductions;  // Kesintiler (Varsa)
    
    private String description;     // "2025 Aralık Maaşı + Mesai" gibi notlar

    // Hangi personelin bordrosu?
    @ManyToOne
    @JoinColumn(name = "personnel_id")
    private Personel personel;

    // Getter & Setter
    public LocalDate getPayrollDate() { return payrollDate; }
    public void setPayrollDate(LocalDate payrollDate) { this.payrollDate = payrollDate; }
    public BigDecimal getGrossSalary() { return grossSalary; }
    public void setGrossSalary(BigDecimal grossSalary) { this.grossSalary = grossSalary; }
    public BigDecimal getNetSalary() { return netSalary; }
    public void setNetSalary(BigDecimal netSalary) { this.netSalary = netSalary; }
    public BigDecimal getDeductions() { return deductions; }
    public void setDeductions(BigDecimal deductions) { this.deductions = deductions; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Personel getPersonel() { return personel; }
    public void setPersonel(Personel personel) { this.personel = personel; }
}