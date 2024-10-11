import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
class Student implements Comparable<Student> {
    private final String name;
    private final String registerNumber;
    private final double CGPA;
    private final List<String> skills;
    private final int aptitudeScore;
    private final List<InterviewSchedule> interviewSchedules;

    public Student(String name, String registerNumber, double CGPA, List<String> skills, int aptitudeScore) {
        this.name = name;
        this.registerNumber = registerNumber;
        this.CGPA = CGPA;
        this.skills = skills;
        this.aptitudeScore = aptitudeScore;
        this.interviewSchedules = new ArrayList<>();
    }
    public void register() {
        String sql = "INSERT INTO student (student_name, register_number, CGPA, skills, aptitude_score) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.registerNumber);
            pstmt.setDouble(3, this.CGPA);
            pstmt.setString(4, String.join(",", this.skills));
            pstmt.setInt(5, this.aptitudeScore);
            pstmt.executeUpdate();
            System.out.println("Student registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getName() {
        return name;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public double getCGPA() {
        return CGPA;
    }

    public List<String> getSkills() {
        return skills;
    }

    public int getQuizMarks() {
        return aptitudeScore;
    }

    public List<InterviewSchedule> getInterviewSchedules() {
        return interviewSchedules;
    }

    public void addInterviewSchedule(InterviewSchedule schedule) {
        this.interviewSchedules.add(schedule);
    }

    @Override
    public int compareTo(Student other) {
        // Comparing by CGPA, then quiz marks
        if (this.CGPA != other.CGPA) {
            return Double.compare(other.CGPA, this.CGPA);
        } else {
            return Integer.compare(other.aptitudeScore, this.aptitudeScore);
        }
    }
}
