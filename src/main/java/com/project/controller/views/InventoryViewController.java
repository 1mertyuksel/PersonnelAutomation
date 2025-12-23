package com.project.controller.views;

import com.project.entity.InventoryItem;
import com.project.service.interfaces.IInventoryItemService;
import com.project.service.interfaces.IPersonelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Inventory (Zimmetli Eşyalar) View Controller
 */
@Controller
@RequestMapping("/inventory")
public class InventoryViewController {

    @Autowired
    private IInventoryItemService inventoryItemService;

    @Autowired
    private IPersonelService personelService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", inventoryItemService.findAll());
        model.addAttribute("activePage", "inventory");
        return "inventory/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("item", new InventoryItem());
        model.addAttribute("personeller", personelService.findAll());
        model.addAttribute("activePage", "inventory");
        model.addAttribute("isEdit", false);
        return "inventory/create";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var itemOpt = inventoryItemService.getByIdAsync(id).join();
        if (itemOpt.isPresent()) {
            model.addAttribute("item", itemOpt.get());
            model.addAttribute("personeller", personelService.findAll());
            model.addAttribute("activePage", "inventory");
            model.addAttribute("isEdit", true);
            return "inventory/edit";
        }
        return "redirect:/inventory?error=Zimmet kaydı bulunamadı";
    }
}


