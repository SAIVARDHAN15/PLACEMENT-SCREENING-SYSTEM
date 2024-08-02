import java.util.*;
class Student implements Comparable<Student> {
    private String name;
    private String registerNumber;
    private double CGPA;
    private List<String> skills;
    private int quizMarks;
    private List<InterviewSchedule> interviewSchedules;

    public Student(String name, String registerNumber, double CGPA, List<String> skills, int quizMarks) {
        this.name = name;
        this.registerNumber = registerNumber;
        this.CGPA = CGPA;
        this.skills = skills;
        this.quizMarks = quizMarks;
        this.interviewSchedules = new ArrayList<>();
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
        return quizMarks;
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
            return Integer.compare(other.quizMarks, this.quizMarks);
        }
    }
}
