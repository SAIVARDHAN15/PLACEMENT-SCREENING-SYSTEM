import java.util.*;
class InterviewSchedule {
    private Company company;
    private Date interviewDate;

    public InterviewSchedule(Company company, Date interviewDate) {
        this.company = company;
        this.interviewDate = interviewDate;
    }

    public Company getCompany() {
        return company;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }
    @Override
    public String toString() {
        return "Interview with " + company.getName() + " on " + interviewDate;
    }
}