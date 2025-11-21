-- Drop tables if they exist to ensure a clean slate on every fresh start.
DROP TABLE IF EXISTS interview, job_application, job_posting, student, company CASCADE;

-- Create Company Table
-- Stores information about the companies visiting for placements.
CREATE TABLE company (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date_of_visit DATE NOT NULL
);

-- Create Student Table
-- Stores the academic and personal details of each student.
CREATE TABLE student (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    register_number VARCHAR(255) NOT NULL UNIQUE,
    cgpa DOUBLE PRECISION NOT NULL,
    skills TEXT, -- Storing skills as a single text field (e.g., "Java,Spring,SQL")
    aptitude_score INTEGER NOT NULL
);

-- Create Job Posting Table
-- Stores details about each job opening from a company.
CREATE TABLE job_posting (
    id BIGSERIAL PRIMARY KEY,
    job_title VARCHAR(255) NOT NULL,
    job_description TEXT,
    required_skills TEXT,
    number_of_slots INTEGER NOT NULL,
    package_offered DOUBLE PRECISION NOT NULL,
    cgpa_criteria DOUBLE PRECISION NOT NULL,
    company_id BIGINT REFERENCES company(id) ON DELETE CASCADE
);

-- Create a junction table for the many-to-many relationship between students and job applications.
-- This table tracks which student has applied for which job.
CREATE TABLE job_application (
    student_id BIGINT REFERENCES student(id) ON DELETE CASCADE,
    job_posting_id BIGINT REFERENCES job_posting(id) ON DELETE CASCADE,
    PRIMARY KEY (student_id, job_posting_id)
);

-- Create Interview Table
-- This table will store scheduled interviews for students for specific jobs.
CREATE TABLE interview (
    id BIGSERIAL PRIMARY KEY,
    interview_date TIMESTAMP NOT NULL,
    student_id BIGINT REFERENCES student(id) ON DELETE CASCADE,
    job_posting_id BIGINT REFERENCES job_posting(id) ON DELETE CASCADE
);

-- (Optional) Add some initial data for testing purposes.
-- You can uncomment these lines if you want some data to be present when the database starts.
/*
INSERT INTO company (name, date_of_visit) VALUES ('Tech Innovations Inc.', '2025-10-15');
INSERT INTO student (name, register_number, cgpa, skills, aptitude_score) VALUES ('Sai Vardhan', 'BTECH123', 8.5, 'Java,Spring,PostgreSQL', 85);
INSERT INTO job_posting (job_title, job_description, required_skills, number_of_slots, package_offered, cgpa_criteria, company_id)
VALUES ('Software Engineer', 'Develop next-gen applications.', 'Java,Spring Boot', 5, 12.0, 7.5, 1);
*/