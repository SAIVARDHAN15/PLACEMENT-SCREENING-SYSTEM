import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AdminPage {
    private PlacementSystem placementSystem; // Reference to the PlacementSystem

    public AdminPage(PlacementSystem placementSystem) {
        this.placementSystem = placementSystem;
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (password.equals("adminPassword")) { // Replace with secure handling
            System.out.println("Admin Dashboard");
            System.out.println("1. View Registered Students");
            System.out.println("2. View Job Postings");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewRegisteredStudents();
                    break;
                case 2:
                    viewJobPostings();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } else {
            System.out.println("Incorrect password. Access denied.");
        }
    }

    public void viewRegisteredStudents() {
        String sql = "SELECT std_id, name, register_number, CGPA, aptitude_score, skills FROM student";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                System.out.println("No registered students found.");
                return;
            }

            System.out.println("Registered Students:");
            while (rs.next()) {
                int stdId = rs.getInt("std_id");
                String name = rs.getString("name");
                String registerNumber = rs.getString("register_number");
                double CGPA = rs.getDouble("CGPA");
                int aptitudeScore = rs.getInt("aptitude_score");
                String skills = rs.getString("skills");

                System.out.println("Student ID: " + stdId);
                System.out.println("Name: " + name);
                System.out.println("Register Number: " + registerNumber);
                System.out.println("CGPA: " + CGPA);
                System.out.println("Aptitude Score: " + aptitudeScore);
                System.out.println("Skills: " + skills);
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void viewJobPostings() {
        String sql = "SELECT jp.job_id, jp.job_title, jp.job_description, jp.required_skills, jp.package, jp.cgpa_criteria, c.company_name " +
                "FROM job_posting jp " +
                "JOIN company c ON jp.company_ID = c.com_id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                System.out.println("No job postings available.");
                return;
            }

            System.out.println("Job Postings:");
            while (rs.next()) {
                int jobId = rs.getInt("job_id");
                String jobTitle = rs.getString("job_title");
                String jobDescription = rs.getString("job_description");
                String requiredSkills = rs.getString("required_skills");
                double packageOffered = rs.getDouble("package");
                double cgpaCriteria = rs.getDouble("cgpa_criteria");
                String companyName = rs.getString("company_name");

                System.out.println("Job ID: " + jobId);
                System.out.println("Job Title: " + jobTitle);
                System.out.println("Company: " + companyName);
                System.out.println("Job Description: " + jobDescription);
                System.out.println("Required Skills: " + requiredSkills);
                System.out.println("Package Offered: " + packageOffered);
                System.out.println("CGPA Criteria: " + cgpaCriteria);
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
