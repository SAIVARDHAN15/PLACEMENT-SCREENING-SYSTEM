package com.placement_screening_system.Project.controller;

import com.placement_screening_system.Project.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/students")
    public String viewAllStudents(Model model) {
        model.addAttribute("students", adminService.getAllStudents());
        return "admin/students";
    }

    @GetMapping("/companies")
    public String viewAllCompanies(Model model) {
        model.addAttribute("companies", adminService.getAllCompanies());
        return "admin/companies";
    }

    @GetMapping("/jobs")
    public String viewAllJobs(Model model) {
        model.addAttribute("jobPostings", adminService.getAllJobPostings());
        return "admin/jobs";
    }
}
