import java.sql.*;
import java.util.*;
import java.util.Date;

public class PlacementSystem {
    private final Connection connection;

    public PlacementSystem(Connection connection) {
        this.connection = connection;
    }

    // Method for filtering eligible students based on CGPA and quiz marks
    public List<Student> filterEligibleStudents(JobPosting jobPosting) {
        String sql = "SELECT * FROM students WHERE CGPA >= ? AND aptitude_score >= ?";

        List<Student> eligibleStudents = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, jobPosting.getCGPACriteria());
            stmt.setInt(2, 60);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(
                        rs.getString("name"),
                        rs.getString("register_number"),
                        rs.getDouble("CGPA"),
                        Arrays.asList(rs.getString("skills").split(",")),
                        rs.getInt("aptitude_score")
                );
                eligibleStudents.add(student);
            }

            //schedule Interview

            for(Student student : eligibleStudents){
                scheduleInterview(student, jobPosting);
            }


        } catch (SQLException e) {
            System.out.println("Error filtering eligible students: " + e.getMessage());
        }
        return eligibleStudents;
    }

    // Method to schedule interviews
    public void scheduleInterview(Student student, JobPosting jobPosting) {
        String sql = "INSERT INTO interview_schedules (student_id, job_posting_id, interview_date) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Fetching student ID and job posting ID from their respective tables
            int studentId = getStudentIdByRegisterNumber(student.getRegisterNumber());
            int jobPostingId = getJobPostingIdByTitle(jobPosting.getJobTitle());

            if (studentId == -1 || jobPostingId == -1) {
                System.out.println("Error: Student or Job Posting not found.");
                return;
            }

            stmt.setInt(1, studentId);
            stmt.setInt(2, jobPostingId);
            stmt.setDate(3, new java.sql.Date(jobPosting.getCompany().getDateOfVisit().getTime()));

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Interview scheduled successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error scheduling interview: " + e.getMessage());
        }
    }

    // Method to retrieve job posting ID by title
    protected int getJobPostingIdByTitle(String jobTitle) {
        String sql = "SELECT job_id FROM job_posting WHERE job_title = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, jobTitle);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching job posting ID: " + e.getMessage());
        }
        return -1;
    }

    // Method to retrieve student ID by register number
    private int getStudentIdByRegisterNumber(String registerNumber) {
        String sql = "SELECT id FROM students WHERE register_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, registerNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching student ID: " + e.getMessage());
        }
        return -1;
    }

    // Method to display the interview schedules
    public void displayScheduledInterviews() {
        String sql = "SELECT s.name, j.job_title, i.interview_date " +
                "FROM interview_schedules i " +
                "JOIN students s ON i.student_id = s.id " +
                "JOIN job_posting j ON i.job_posting_id = j.id";

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String studentName = rs.getString("name");
                String jobTitle = rs.getString("job_title");
                Date interviewDate = rs.getDate("interview_date");

                System.out.println("Student: " + studentName + " | Job Title: " + jobTitle + " | Interview Date: " + interviewDate);
            }
        } catch (SQLException e) {
            System.out.println("Error displaying interview schedules: " + e.getMessage());
        }
    }
}
