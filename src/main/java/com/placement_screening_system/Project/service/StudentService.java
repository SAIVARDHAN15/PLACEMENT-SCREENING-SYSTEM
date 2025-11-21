package com.placement_screening_system.Project.service;

import com.placement_screening_system.Project.domain.JobPosting;
import com.placement_screening_system.Project.domain.Student;
import com.placement_screening_system.Project.repository.JobPostingRepository;
import com.placement_screening_system.Project.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final JobPostingRepository jobPostingRepository;

    @Transactional
    public Student registerStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public void applyForJob(String registerNumber, Long jobId) {
        Student student = studentRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new RuntimeException("Student not found with register number: " + registerNumber));

        JobPosting jobPosting = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + jobId));

        jobPosting.getApplicants().add(student);
        jobPostingRepository.save(jobPosting);
    }
}