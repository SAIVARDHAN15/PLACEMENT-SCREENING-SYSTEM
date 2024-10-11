import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CompanyPage {
    Company company;
    boolean isRegistered = false;
    boolean jobPosted = false;
    public void display() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Company Dashboard");
        System.out.println("1. Register Company");
        System.out.println("2. Post Job");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                register(scanner);
                break;
            case 2:
                if(isRegistered){
                    postJob(scanner);
                }
                else{
                    register(scanner);
                }
                break;
            case 3:
                System.out.println("Enter Job title: ");
                String jobTitle = scanner.nextLine();
                if(isRegistered && jobPosted){
                    filterEligibleStudents(this.company.getName(), jobTitle);
                }
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void register(Scanner scanner) {
        System.out.println("Enter company name:");
        String companyName = scanner.nextLine();
        System.out.print("Enter date of visit (DD-MM-YYYY): ");
        String visitDateStr = scanner.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        Date visitDate = null;
        try {
            visitDate = dateFormat.parse(visitDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter date in dd-MM-yyyy format.");
        } finally {
            scanner.close();
        }
        if(visitDate != null){
            this.company = new Company(companyName, visitDate);
        }
        this.isRegistered = true;

    }

    private void postJob(Scanner scanner) {
        System.out.print("Enter Job Title: ");
        String jobTitle = scanner.nextLine();
        System.out.print("Enter Job Description: ");
        String jobDescription = scanner.nextLine();
        System.out.print("Enter Required Skills (comma separated): ");
        List<String> requiredSkills = List.of(scanner.nextLine().split(","));
        System.out.print("Enter Number of Slots: ");
        int numberOfSlots = scanner.nextInt();
        System.out.print("Enter Package Offered: ");
        double packageOffered = scanner.nextDouble();
        System.out.print("Enter CGPACriteria: ");
        int CGPACriteria = scanner.nextInt();
        JobPosting jobPosting = new JobPosting(jobTitle, jobDescription, requiredSkills, numberOfSlots, packageOffered, CGPACriteria,this.company);
        company.postJob(jobPosting);
        this.jobPosted = true;
    }

    public void filterEligibleStudents(String companyName, String jobTitle) {
        String fetchJobIdSql = "SELECT job_id FROM job_posting WHERE company_ID = (SELECT com_id FROM company WHERE company_name = ?) AND job_title = ?";
        String fetchEligibleStudentsSql =
                "SELECT s.std_id, s.name, s.register_number, s.CGPA, s.skills " +
                        "FROM student s " +
                        "JOIN job_applications ja ON s.std_id = ja.std_id " +
                        "WHERE ja.job_id = ?";

        int jobId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement fetchJobStmt = conn.prepareStatement(fetchJobIdSql);
             PreparedStatement fetchStudentsStmt = conn.prepareStatement(fetchEligibleStudentsSql)) {

            // Fetch job_id from job_postings by company name and job title
            fetchJobStmt.setString(1, companyName);
            fetchJobStmt.setString(2, jobTitle);
            ResultSet rsJob = fetchJobStmt.executeQuery();
            if (rsJob.next()) {
                jobId = rsJob.getInt("job_id");
            } else {
                System.out.println("Job not found for the given company and job title.");
                return;
            }

            // Fetch eligible students who applied for the job
            fetchStudentsStmt.setInt(1, jobId);
            ResultSet rsStudents = fetchStudentsStmt.executeQuery();

            // If no students applied, display a message
            if (!rsStudents.isBeforeFirst()) {
                System.out.println("No students have applied for this job.");
                return;
            }

            // Print eligible students
            System.out.println("Eligible Students for Job (" + jobTitle + " at " + companyName + "):");
            while (rsStudents.next()) {
                int stdId = rsStudents.getInt("std_id");
                String name = rsStudents.getString("name");
                String registerNumber = rsStudents.getString("register_number");
                double CGPA = rsStudents.getDouble("CGPA");
                String skills = rsStudents.getString("skills");

                System.out.println("Student ID: " + stdId);
                System.out.println("Name: " + name);
                System.out.println("Register Number: " + registerNumber);
                System.out.println("CGPA: " + CGPA);
                System.out.println("Skills: " + skills);
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}