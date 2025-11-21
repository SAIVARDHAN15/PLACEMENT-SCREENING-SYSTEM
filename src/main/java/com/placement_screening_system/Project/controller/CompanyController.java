package com.placement_screening_system.Project.controller;

import com.placement_screening_system.Project.domain.Company;
import com.placement_screening_system.Project.domain.JobPosting;
import com.placement_screening_system.Project.repository.CompanyRepository;
import com.placement_screening_system.Project.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("company", new Company());
        return "company/register";
    }

    @PostMapping("/register")
    public String registerCompany(@ModelAttribute Company company) {
        companyService.registerCompany(company);
        return "company/register";
    }

    @GetMapping("/{companyId}/post-job")
    public String showPostJobForm(@PathVariable Long companyId, Model model) {
        model.addAttribute("jobPosting", new JobPosting());
        model.addAttribute("companyId", companyId);
        return "company/post-job";
    }

    @PostMapping("/{companyId}/post-job")
    public String postJob(@PathVariable Long companyId, @ModelAttribute JobPosting jobPosting) {
        companyService.postJob(companyId, jobPosting);
        return "redirect:/company/" + companyId;
    }

    @GetMapping("/{companyId}")
    public String companyDashboard(@PathVariable Long companyId, Model model) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        model.addAttribute("company", company);
        return "company/dashboard";
    }
}
