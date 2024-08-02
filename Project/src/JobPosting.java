import java.util.*;

// Class representing a Job Posting
class JobPosting {
    private String jobTitle;
    private String jobDescription;
    private List<String> requiredSkills;
    private int numberOfSlots;
    private double packageOffered;
    private List<Interview> scheduledInterviews;
    private Company company;

    public JobPosting(String jobTitle, String jobDescription, List<String> requiredSkills, int numberOfSlots, double packageOffered, Company company) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.requiredSkills = requiredSkills;
        this.numberOfSlots = numberOfSlots;
        this.packageOffered = packageOffered;
        this.scheduledInterviews = new ArrayList<>();
        this.company = company;

    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public int getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setNumberOfSlots(int numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }

    public double getPackageOffered() {
        return packageOffered;
    }

    public void setPackageOffered(double packageOffered) {
        this.packageOffered = packageOffered;
    }
    public Company getCompany(){
        return company;
    }
    public List<Interview> getScheduledInterviews() {
        return scheduledInterviews;
    }

    public void scheduleInterview(Interview interview) {
        this.scheduledInterviews.add(interview);
    }
}
