package com.placement_screening_system.Project.controller;

import com.placement_screening_system.Project.domain.Student;
import com.placement_screening_system.Project.repository.JobPostingRepository;
import com.placement_screening_system.Project.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final JobPostingRepository jobPostingRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/register";
    }

    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student) {
        studentService.registerStudent(student);
        return "redirect:/";
    }

    @GetMapping("/jobs")
    public String listJobs(Model model) {
        model.addAttribute("jobPostings", jobPostingRepository.findAll());
        return "student/jobs";
    }

    @PostMapping("/apply")
    public String applyForJob(@RequestParam String registerNumber, @RequestParam Long jobId) {
        studentService.applyForJob(registerNumber, jobId);
        return "redirect:/student/jobs";
    }
}
