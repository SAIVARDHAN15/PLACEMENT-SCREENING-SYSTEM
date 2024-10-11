import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StudentPage {
    private boolean isRegistered;

    StudentPage(){
        this.isRegistered = false;
    }
    public void display() {
        Scanner scanner = new Scanner(System.in);

        while(true){
        System.out.println("Student Dashboard");
        System.out.println("1. Register");
        System.out.println("2. Apply for a Job");
        System.out.println("3. Exit");
        // Other options can be added

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerStudent(scanner);
                    break;
                case 2:
                    applyForJob(scanner);
                    break;
                case 3:
                    continue;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    private Student registerStudent(Scanner scanner) {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Register Number: ");
        String registerNumber = scanner.nextLine();
        System.out.print("Enter CGPA: ");
        double CGPA = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Skills (comma separated): ");
        List<String> skills = List.of(scanner.nextLine().split(","));
        System.out.print("Enter Aptitude score: ");
        int aptitudeScore = scanner.nextInt();
        Student student = new Student(name, registerNumber, CGPA, skills, aptitudeScore);
        student.register();
        this.isRegistered = true;
        return student;
    }

    private void applyForJob(Scanner scanner) {
        if(isRegistered || isTableNotEmpty("student")){
            System.out.println("1. view visited companies so far");
            System.out.println("2. view recent job openings");
            System.out.println("3. register for job");

            int select = scanner.nextInt();

            switch (select){

                case 1:
                    viewVisitedCompanies(scanner);
                    break;
                case 2:
                    listAllJobOpenings();
                    break;
                case 3:
                    System.out.println("Enter your register number: ");
                    String regNum = scanner.nextLine();
                    scanner.nextLine();
                    System.out.println("Enter company's name: ");
                    String companyName = scanner.nextLine();
                    registerForJob(regNum, companyName);
                    break;
                default:
                    System.out.println("Enter valid input");
            }
        }
        else{
            System.err.println("!!you have to register first!!");
            registerStudent(scanner);
            applyForJob(scanner);
        }
    }
    public void viewVisitedCompanies(Scanner scanner) {
        System.out.println("Filter by:\n1. Show all\n2. By name\n3. By package\n4. By skill");
        int choice = scanner.nextInt();
        String name = "";
        double minPackage = 0;
        String skill = "";
        scanner.nextLine(); // Consume newline
        List<JobPosting> companies = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "";
            switch (choice) {
                case 1: // Show all
                    sql = "SELECT * FROM companies_visited";
                    break;
                case 2: // By name
                    System.out.print("Enter Company Name: ");
                    name = scanner.nextLine();
                    sql = "SELECT * FROM companies_visited WHERE company_name LIKE ?";
                    break;
                case 3: // By package
                    System.out.print("Enter Minimum Package: ");
                    minPackage = scanner.nextDouble();
                    sql = "SELECT * FROM companies_visited WHERE package >= ?";
                    break;
                case 4: // By skill
                    System.out.print("Enter Skill: ");
                    skill = scanner.nextLine();
                    sql = "SELECT * FROM companies_visited WHERE required_skills LIKE ?";
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                if (choice == 2) {
                    pstmt.setString(1, "%" + name + "%");
                } else if (choice == 3) {
                    pstmt.setDouble(1, minPackage);
                } else if (choice == 4) {
                    pstmt.setString(1, "%" + skill + "%");
                }

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String companyName = rs.getString("company_name");
                    String jobTitle = rs.getString("job_title");
                    double packageOffered = rs.getDouble("package");
                    String requiredSkills = rs.getString(Arrays.asList("required_skills".split(",")).toString());
                    List<String> skills = List.of(requiredSkills.split(","));
                    companies.add(new JobPosting(companyName, packageOffered, skills, jobTitle));
                }

                if (companies.isEmpty()) {
                    System.out.println("No companies found.");
                } else {
                    System.out.println("Visited Companies:");
                    for (JobPosting company : companies) {
                        System.out.println(company);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<JobPosting> listAllJobOpenings() {
        List<JobPosting> jobPostings = new ArrayList<>();
        String sql = "SELECT job_title, job_description, required_skills, package FROM job_posting";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String companyName = rs.getString("company_name");
                String jobTitle = rs.getString("job_title");
                String jobDescription = rs.getString("job_description");
                String skillsString = rs.getString("required_skills");
                double packageOffered = rs.getDouble("package");
                // Split the skills string into a list
                List<String> requiredSkills = List.of(skillsString.split(","));
                // Create a new JobPosting object and add it to the list
                JobPosting jobPosting = new JobPosting(companyName,packageOffered,jobDescription, requiredSkills,jobTitle );
                jobPostings.add(jobPosting);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobPostings;
    }

    private void registerForJob(String registerNumber, String companyName) {
        String fetchStudentIdSql = "SELECT std_id FROM student WHERE register_number = ?";
        String fetchJobIdSql = "SELECT job_id FROM job_posting WHERE company_ID = (SELECT com_id FROM company WHERE company_name = ?)";
        String insertApplicationSql = "INSERT INTO job_applications (std_id, job_id) VALUES (?, ?)";

        int stdId = -1;
        int jobId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement fetchStudentStmt = conn.prepareStatement(fetchStudentIdSql);
             PreparedStatement fetchJobStmt = conn.prepareStatement(fetchJobIdSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertApplicationSql)) {

            // Fetch std_id from student table
            fetchStudentStmt.setString(1, registerNumber);
            ResultSet rsStudent = fetchStudentStmt.executeQuery();
            if (rsStudent.next()) {
                stdId = rsStudent.getInt("std_id");
            } else {
                System.out.println("Student with register number " + registerNumber + " not found.");
                return;
            }

            // Fetch job_id from job_posting table
            fetchJobStmt.setString(1, companyName);
            ResultSet rsJob = fetchJobStmt.executeQuery();
            if (rsJob.next()) {
                jobId = rsJob.getInt("job_id");
            } else {
                System.out.println("Job from company " + companyName + " not found.");
                return;
            }

            // Check if student has already applied for this job
            String checkApplicationSql = "SELECT COUNT(*) FROM job_applications WHERE std_id = ? AND job_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkApplicationSql);
            checkStmt.setInt(1, stdId);
            checkStmt.setInt(2, jobId);
            ResultSet checkRs = checkStmt.executeQuery();
            if (checkRs.next() && checkRs.getInt(1) > 0) {
                System.out.println("You have already applied for this job.");
                return;
            }

            // Insert into job_applications if not already applied
            insertStmt.setInt(1, stdId);
            insertStmt.setInt(2, jobId);
            insertStmt.executeUpdate();
            System.out.println("Application submitted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean isTableNotEmpty(String tableName) {
        String query = "SELECT 1 FROM " + tableName + " LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            return rs.next();  // Returns true if a row exists
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Return false if no rows or an exception occurs
    }
}
