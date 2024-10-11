import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
class Company {
    private String name;
    private final Date dateOfVisit;

    public Company(String name, Date dateOfVisit) {
        this.name = name;
        this.dateOfVisit = dateOfVisit;
    }

    public void postJob(JobPosting jobPosting) {

        String sql = "INSERT INTO job_posting (company_ID , job_title, job_description, required_skills, package, cgpa_criteria, com_id) VALUES (?, ?, ?, ?, ?);";
        String getComIdSql = "SELECT com_id FROM company WHERE company_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement getComIdStmt = conn.prepareStatement(getComIdSql);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = getComIdStmt.executeQuery();
            if(rs.next()) {
                int com_id = rs.getInt("com_id");
                pstmt.setString(1, jobPosting.getCompany().getName());
                pstmt.setString(2, jobPosting.getJobTitle());
                pstmt.setString(3, jobPosting.getJobDescription());
                pstmt.setString(4, String.join(",", jobPosting.getRequiredSkills()));
                pstmt.setDouble(5, jobPosting.getPackageOffered());
                pstmt.setInt(6, jobPosting.getCGPACriteria());
                pstmt.setInt(7, com_id);
                pstmt.executeUpdate();
                System.out.println("Job posted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getName() {
        return name;
    }
    public Date getDateOfVisit() {
        return dateOfVisit;
    }
}

