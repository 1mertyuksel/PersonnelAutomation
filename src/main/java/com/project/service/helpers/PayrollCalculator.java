package com.project.service.helpers;

import com.project.entity.Payroll;
import com.project.entity.Personel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * Bordro hesaplama mantığını içeren helper sınıf.
 * Tüm maaş hesaplamaları bu sınıf üzerinden yürütülür.
 */
public class PayrollCalculator {

    /**
     * Temel hesaplama modeli:
     * - Günlük ücret = netMaaş / 30
     * - Fazla mesai saat ücreti = (günlükÜcret / 8) * 1.5
     * - Brüt = (günlükÜcret * daysWorked) + (mesaiSaat * mesaiSaatÜcreti)
     * - Kesintiler (örnek): SGK %14 + GelirVergisi %15 toplam ~%29
     * - Net = Brüt - Kesinti
     */
    public Payroll calculate(Personel personel,
                             int daysWorked,
                             int overtimeHours,
                             LocalDate payrollDate,
                             String description) {

        if (personel.getSalary() == null) {
            throw new IllegalArgumentException("Personelin net maaşı tanımlı değil.");
        }

        BigDecimal netMonthly = personel.getSalary();
        BigDecimal daily = netMonthly.divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP);

        // Çalışılan gün brütü
        BigDecimal workedGross = daily.multiply(BigDecimal.valueOf(daysWorked));

        // Fazla mesai ücreti
        BigDecimal hourly = daily.divide(BigDecimal.valueOf(8), 4, RoundingMode.HALF_UP);
        BigDecimal overtimeRate = hourly.multiply(BigDecimal.valueOf(1.5));
        BigDecimal overtimeGross = overtimeRate.multiply(BigDecimal.valueOf(overtimeHours));

        BigDecimal gross = workedGross.add(overtimeGross).setScale(2, RoundingMode.HALF_UP);

        // Kesinti oranı (örnek): %29
        BigDecimal deductionRate = BigDecimal.valueOf(0.29);
        BigDecimal deductions = gross.multiply(deductionRate).setScale(2, RoundingMode.HALF_UP);

        BigDecimal net = gross.subtract(deductions).setScale(2, RoundingMode.HALF_UP);

        Payroll payroll = new Payroll();
        payroll.setPersonel(personel);
        payroll.setPayrollDate(payrollDate != null ? payrollDate : LocalDate.now());
        payroll.setGrossSalary(gross);
        payroll.setDeductions(deductions);
        payroll.setNetSalary(net);
        payroll.setDescription(description != null ? description : "Otomatik oluşturulan bordro");

        return payroll;
    }
}


