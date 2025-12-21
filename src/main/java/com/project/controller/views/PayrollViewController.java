package com.project.controller.views;

import com.project.service.interfaces.IPayrollService;
import com.project.service.interfaces.IPersonelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Payroll View Controller
 */
@Controller
@RequestMapping("/payroll")
public class PayrollViewController {

    @Autowired
    private IPayrollService payrollService;
    
    @Autowired
    private IPersonelService personelService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("personeller", personelService.findAll());
        model.addAttribute("activePage", "payroll");
        return "payroll/index";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("payrolls", payrollService.findAll());
        return "payroll/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        payrollService.getByIdAsync(id).join().ifPresent(payroll -> {
            model.addAttribute("payroll", payroll);
        });
        return "payroll/view";
    }
}

