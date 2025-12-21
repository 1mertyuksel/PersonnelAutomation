package com.project.controller.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Report View Controller
 */
@Controller
@RequestMapping("/reports")
public class ReportViewController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("activePage", "reports");
        return "reports/index";
    }
}
