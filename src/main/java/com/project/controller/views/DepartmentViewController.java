package com.project.controller.views;

import com.project.entity.Department;
import com.project.service.interfaces.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Department View Controller
 * Departman yönetimi sayfaları
 */
@Controller
@RequestMapping("/departments")
public class DepartmentViewController {

    @Autowired
    private IDepartmentService departmentService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("activePage", "departments");
        return "department/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("activePage", "departments");
        model.addAttribute("isEdit", false);
        return "department/create";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var deptOpt = departmentService.getByIdAsync(id).join();
        if (deptOpt.isPresent()) {
            model.addAttribute("department", deptOpt.get());
            model.addAttribute("activePage", "departments");
            model.addAttribute("isEdit", true);
            return "department/edit";
        }
        return "redirect:/departments?error=Departman bulunamadı";
    }
}


