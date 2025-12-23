package com.project.controller.views;

import com.project.entity.Personel;
import com.project.service.interfaces.IPersonelService;
import com.project.service.interfaces.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Personel View Controller
 */
@Controller
@RequestMapping("/personel")
public class PersonelViewController {

    @Autowired
    private IPersonelService personelService;

    @Autowired
    private IDepartmentService departmentService;

    @GetMapping
    public String list(Model model, @RequestParam(required = false) Long departmentId) {
        var allPersonel = personelService.findAll();

        if (departmentId != null) {
            allPersonel = allPersonel.stream()
                .filter(p -> p.getDepartment() != null && departmentId.equals(p.getDepartment().getId()))
                .toList();
        }

        model.addAttribute("personeller", allPersonel);
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("selectedDepartmentId", departmentId);
        model.addAttribute("activePage", "personel");
        return "personel/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("personel", new Personel());
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("activePage", "personel");
        model.addAttribute("isEdit", false);
        return "personel/create";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var personelOpt = personelService.getByIdAsync(id).join();
        if (personelOpt.isPresent()) {
            model.addAttribute("personel", personelOpt.get());
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("activePage", "personel");
            model.addAttribute("isEdit", true);
            return "personel/edit";
        } else {
            return "redirect:/personel?error=Personel bulunamadı";
        }
    }
}

