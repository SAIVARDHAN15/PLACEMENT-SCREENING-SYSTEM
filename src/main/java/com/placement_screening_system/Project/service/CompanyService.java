package com.placement_screening_system.Project.service;

import com.placement_screening_system.Project.domain.Company;
import com.placement_screening_system.Project.domain.JobPosting;
import com.placement_screening_system.Project.repository.CompanyRepository;
import com.placement_screening_system.Project.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final JobPostingRepository jobPostingRepository;

    @Transactional
    public Company registerCompany(Company company) {
        return companyRepository.save(company);
    }

    @Transactional
    public JobPosting postJob(Long companyId, JobPosting jobPosting) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        jobPosting.setCompany(company);
        return jobPostingRepository.save(jobPosting);
    }
}
