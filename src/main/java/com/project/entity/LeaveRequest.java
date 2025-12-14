package com.project.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
public class LeaveRequest extends BaseEntity {

    private LocalDate startDate;
    private LocalDate endDate;
    private int daysCount; // Kaç gün izin kullandı?
    
    private String status; // "ONAYLANDI", "REDDEDILDI", "BEKLIYOR"

    // Hangi personel izin istedi?
    @ManyToOne
    @JoinColumn(name = "personnel_id")
    private Personel personel;

    // Getter & Setter
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public int getDaysCount() { return daysCount; }
    public void setDaysCount(int daysCount) { this.daysCount = daysCount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Personel getPersonel() { return personel; }
    public void setPersonel(Personel personel) { this.personel = personel; }
}