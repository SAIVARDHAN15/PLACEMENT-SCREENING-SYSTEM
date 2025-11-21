package com.placement_screening_system.Project.service;

import com.placement_screening_system.Project.domain.Company;
import com.placement_screening_system.Project.domain.JobPosting;
import com.placement_screening_system.Project.domain.Student;
import com.placement_screening_system.Project.repository.CompanyRepository;
import com.placement_screening_system.Project.repository.JobPostingRepository;
import com.placement_screening_system.Project.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final StudentRepository studentRepository;
    private final JobPostingRepository jobPostingRepository;
    private final CompanyRepository companyRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<JobPosting> getAllJobPostings() {
        return jobPostingRepository.findAll();
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
