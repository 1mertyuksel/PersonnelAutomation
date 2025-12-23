package com.project.config;

import com.project.entity.Department;
import com.project.entity.Personel;
import com.project.service.interfaces.IDepartmentService;
import com.project.service.interfaces.IPersonelService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Uygulama ilk açıldığında örnek veri üretmek için kullanılan seeder.
 * 50 adet örnek personel kaydını mevcut departmanlara rastgele dağıtır.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final IPersonelService personelService;
    private final IDepartmentService departmentService;

    private final Random random = new Random();

    public DataSeeder(IPersonelService personelService,
                      IDepartmentService departmentService) {
        this.personelService = personelService;
        this.departmentService = departmentService;
    }

    @Override
    public void run(String... args) {
        // Zaten yeterince personel varsa örnek veri ekleme
        if (personelService.findAll().size() >= 50) {
            return;
        }

        List<Department> departments = departmentService.findAll();
        if (departments.isEmpty()) {
            // Departman yoksa hiçbir şey yapma; tablo bozuk görünmesin
            return;
        }

        List<String> firstNames = Arrays.asList(
                "Ahmet", "Mehmet", "Ayşe", "Fatma", "Can", "Ece",
                "Burak", "Deniz", "Elif", "Mert", "Zeynep", "Hakan",
                "Gamze", "Berk", "Selin", "Emre", "Melis", "Onur",
                "Nur", "Tolga"
        );

        List<String> lastNames = Arrays.asList(
                "Yılmaz", "Demir", "Kaya", "Çelik", "Şahin", "Yıldız",
                "Aydın", "Koç", "Arslan", "Doğan", "Korkmaz", "Çetin",
                "Öztürk", "Polat", "Taş", "Erdoğan", "Kaplan", "Güneş",
                "Bozkurt", "Karaca"
        );

        for (int i = 0; i < 50; i++) {
            Personel p = new Personel();
            p.setFirstName(randomFrom(firstNames));
            p.setLastName(randomFrom(lastNames));

            // Rastgele departman
            Department dep = departments.get(random.nextInt(departments.size()));
            p.setDepartment(dep);

            // Rastgele işe giriş tarihi (son 10 yıl içinde)
            int yearsBack = random.nextInt(10); // 0-9 yıl geriye
            int daysBack = random.nextInt(365);
            LocalDate hireDate = LocalDate.now().minusYears(yearsBack).minusDays(daysBack);
            p.setHireDate(hireDate);

            // Rastgele maaş (20k - 80k arası)
            int salary = 20000 + random.nextInt(60001);
            p.setSalary(BigDecimal.valueOf(salary));

            // İzin bilgileri
            int quota = 14 + random.nextInt(11); // 14 - 24
            p.setAnnualLeaveQuota(quota);
            p.setUsedLeaveDays(random.nextInt(quota + 1));

            personelService.addAsync(p).join();
        }
    }

    private <T> T randomFrom(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}


