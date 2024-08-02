import java.util.*;
class Interview {
    private Student student;
    private JobPosting jobPosting;
    private Date interviewDate;

    public Interview(Student student, JobPosting jobPosting, Date interviewDate) {
        this.student = student;
        this.jobPosting = jobPosting;
        this.interviewDate = interviewDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }
}