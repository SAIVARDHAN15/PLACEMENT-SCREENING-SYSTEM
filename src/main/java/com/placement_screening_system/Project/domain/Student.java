package com.placement_screening_system.Project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String registerNumber;

    private double cgpa;
    private String skills; // Storing as comma-separated string
    private int aptitudeScore;

    @ManyToMany(mappedBy = "applicants")
    private Set<JobPosting> appliedJobs;

    public Student(String name, String registerNumber, double cgpa, String skills, int aptitudeScore) {
        this.name = name;
        this.registerNumber = registerNumber;
        this.cgpa = cgpa;
        this.skills = skills;
        this.aptitudeScore = aptitudeScore;
    }
}
