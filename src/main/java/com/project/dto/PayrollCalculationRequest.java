package com.project.dto;

import java.time.LocalDate;

/**
 * Bordro hesaplama isteği için kullanılan DTO.
 * Frontend formundan gelen veriyi temsil eder.
 */
public class PayrollCalculationRequest {

    private Long personelId;
    private Integer daysWorked;   // Ay içinde çalışılan gün
    private Integer overtime;     // Fazla mesai (saat)
    private LocalDate payrollDate;
    private String description;

    public Long getPersonelId() {
        return personelId;
    }

    public void setPersonelId(Long personelId) {
        this.personelId = personelId;
    }

    public Integer getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(Integer daysWorked) {
        this.daysWorked = daysWorked;
    }

    public Integer getOvertime() {
        return overtime;
    }

    public void setOvertime(Integer overtime) {
        this.overtime = overtime;
    }

    public LocalDate getPayrollDate() {
        return payrollDate;
    }

    public void setPayrollDate(LocalDate payrollDate) {
        this.payrollDate = payrollDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


