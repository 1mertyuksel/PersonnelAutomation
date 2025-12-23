package com.project.service.services;

import com.project.entity.InventoryItem;
import com.project.entity.LeaveRequest;
import com.project.entity.Payroll;
import com.project.entity.Personel;
import com.project.service.interfaces.IInventoryItemService;
import com.project.service.interfaces.ILeaveRequestService;
import com.project.service.interfaces.IPayrollService;
import com.project.service.interfaces.IPersonelService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class ReportExportService {

    private final IPersonelService personelService;
    private final IPayrollService payrollService;
    private final ILeaveRequestService leaveRequestService;
    private final IInventoryItemService inventoryItemService;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public ReportExportService(IPersonelService personelService,
                               IPayrollService payrollService,
                               ILeaveRequestService leaveRequestService,
                               IInventoryItemService inventoryItemService) {
        this.personelService = personelService;
        this.payrollService = payrollService;
        this.leaveRequestService = leaveRequestService;
        this.inventoryItemService = inventoryItemService;
    }

    /**
     * Tüm ana tabloların verilerini tek bir CSV dosyasında dışa aktarır.
     */
    public byte[] exportAllAsCsv() {
        StringBuilder sb = new StringBuilder();

        exportPersonel(sb);
        sb.append("\n");
        exportPayroll(sb);
        sb.append("\n");
        exportLeaveRequests(sb);
        sb.append("\n");
        exportInventory(sb);

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    private void exportPersonel(StringBuilder sb) {
        List<Personel> list = personelService.findAll();
        sb.append("# PERSONEL LISTESI\n");
        sb.append("ID;Ad;Soyad;Departman;Ise Giris;Maas;Yillik Izin;Kullanilan Izin\n");
        for (Personel p : list) {
            sb.append(p.getId()).append(";");
            sb.append(safe(p.getFirstName())).append(";");
            sb.append(safe(p.getLastName())).append(";");
            sb.append(p.getDepartment() != null ? safe(p.getDepartment().getName()) : "").append(";");
            sb.append(p.getHireDate() != null ? p.getHireDate().format(DATE_FMT) : "").append(";");
            sb.append(p.getSalary() != null ? p.getSalary() : "").append(";");
            sb.append(p.getAnnualLeaveQuota()).append(";");
            sb.append(p.getUsedLeaveDays()).append("\n");
        }
    }

    private void exportPayroll(StringBuilder sb) {
        List<Payroll> list = payrollService.findAll();
        sb.append("# BORDRO KAYITLARI\n");
        sb.append("ID;Personel;Tarih;Brut;Net;Kesinti;Aciklama\n");
        for (Payroll p : list) {
            sb.append(p.getId()).append(";");
            sb.append(p.getPersonel() != null ? safe(p.getPersonel().getFirstName() + " " + p.getPersonel().getLastName()) : "").append(";");
            sb.append(p.getPayrollDate() != null ? p.getPayrollDate().format(DATE_FMT) : "").append(";");
            sb.append(p.getGrossSalary() != null ? p.getGrossSalary() : "").append(";");
            sb.append(p.getNetSalary() != null ? p.getNetSalary() : "").append(";");
            sb.append(p.getDeductions() != null ? p.getDeductions() : "").append(";");
            sb.append(safe(p.getDescription())).append("\n");
        }
    }

    private void exportLeaveRequests(StringBuilder sb) {
        List<LeaveRequest> list = leaveRequestService.findAll();
        sb.append("# IZIN TALEPLERI\n");
        sb.append("ID;Personel;Baslangic;Bitis;Gun;Durum\n");
        for (LeaveRequest r : list) {
            sb.append(r.getId()).append(";");
            sb.append(r.getPersonel() != null ? safe(r.getPersonel().getFirstName() + " " + r.getPersonel().getLastName()) : "").append(";");
            sb.append(r.getStartDate() != null ? r.getStartDate().format(DATE_FMT) : "").append(";");
            sb.append(r.getEndDate() != null ? r.getEndDate().format(DATE_FMT) : "").append(";");
            sb.append(r.getDaysCount()).append(";");
            sb.append(safe(r.getStatus())).append("\n");
        }
    }

    private void exportInventory(StringBuilder sb) {
        List<InventoryItem> list = inventoryItemService.findAll();
        sb.append("# ZIMMETLI ESYALAR\n");
        sb.append("ID;Esya Adi;Seri No;Zimmet Tarihi;Personel\n");
        for (InventoryItem i : list) {
            sb.append(i.getId()).append(";");
            sb.append(safe(i.getProductName())).append(";");
            sb.append(safe(i.getSerialNumber())).append(";");
            sb.append(i.getAssignedDate() != null ? i.getAssignedDate().format(DATE_FMT) : "").append(";");
            sb.append(i.getPersonel() != null ? safe(i.getPersonel().getFirstName() + " " + i.getPersonel().getLastName()) : "").append("\n");
        }
    }

    private String safe(String value) {
        if (value == null) return "";
        // CSV içinde noktalı virgülü virgüle çevir
        return value.replace(";", ",");
    }
}


