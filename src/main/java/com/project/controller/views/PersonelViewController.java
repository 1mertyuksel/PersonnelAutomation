package com.project.controller.views;

import com.project.entity.Personel;
import com.project.service.interfaces.IPersonelService;
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

    @GetMapping
    public String list(Model model, @RequestParam(required = false) String department) {
        var allPersonel = personelService.findAll();
        
        if (department != null && !department.isEmpty() && !"Tüm Departmanlar".equals(department)) {
            allPersonel = allPersonel.stream()
                .filter(p -> department.equals(p.getDepartment()))
                .toList();
        }
        
        model.addAttribute("personeller", allPersonel);
        model.addAttribute("selectedDepartment", department != null ? department : "Tüm Departmanlar");
        model.addAttribute("activePage", "personel");
        return "personel/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("personel", new Personel());
        model.addAttribute("activePage", "personel");
        model.addAttribute("isEdit", false);
        return "personel/create";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var personelOpt = personelService.getByIdAsync(id).join();
        if (personelOpt.isPresent()) {
            model.addAttribute("personel", personelOpt.get());
            model.addAttribute("activePage", "personel");
            model.addAttribute("isEdit", true);
            return "personel/edit";
        } else {
            return "redirect:/personel?error=Personel bulunamadı";
        }
    }
}

