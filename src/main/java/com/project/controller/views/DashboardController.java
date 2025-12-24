package com.project.controller.views;

import com.project.entity.Personel;
import com.project.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Dashboard Controller
 */
@Controller
public class DashboardController {

    @Autowired
    private IPersonelService personelService;
    
    @Autowired
    private IInventoryItemService inventoryService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Personel> allPersonel = personelService.findAll();
        
        // Toplam personel sayısı
        int personelCount = allPersonel.size();
        
        // Toplam maaş yükü (TL cinsinden)
        BigDecimal totalSalary = allPersonel.stream()
            .filter(p -> p.getSalary() != null)
            .map(Personel::getSalary)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Türkçe format: binlik ayırıcı nokta, ondalık ayırıcı virgül
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.forLanguageTag("tr-TR"));
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        String totalSalaryFormatted = df.format(totalSalary);
        
        // Zimmetli eşya sayısı
        int inventoryCount = inventoryService.findAll().size();
        
        // Departman dağılımı (Department entity üzerinden)
        Map<com.project.entity.Department, Long> deptCount = allPersonel.stream()
            .filter(p -> p.getDepartment() != null)
            .collect(Collectors.groupingBy(Personel::getDepartment, Collectors.counting()));
        
        List<Map<String, Object>> departmentStats = deptCount.entrySet().stream()
            .map(entry -> {
                Map<String, Object> stat = new HashMap<>();
                String name = entry.getKey() != null ? entry.getKey().getName() : "Belirtilmemiş";
                stat.put("name", name);
                double percentage = personelCount > 0 ? (entry.getValue().doubleValue() / personelCount) * 100 : 0;
                stat.put("percentage", Math.round(percentage));
                return stat;
            })
            .collect(Collectors.toList());
        
        model.addAttribute("personelCount", personelCount);
        model.addAttribute("totalSalary", totalSalary);
        model.addAttribute("totalSalaryFormatted", totalSalaryFormatted);
        model.addAttribute("inventoryCount", inventoryCount);
        model.addAttribute("departmentStats", departmentStats);
        model.addAttribute("activePage", "dashboard");
        return "dashboard/index";
    }
}

