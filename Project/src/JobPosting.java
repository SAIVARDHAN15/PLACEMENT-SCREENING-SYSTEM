import java.util.*;

// Class representing a Job Posting
class JobPosting {
    private String jobTitle;
    private String jobDescription;
    private List<String> requiredSkills;
    private int numberOfSlots;
    private double packageOffered;
    private int CGPACriteria;
    private List<Interview> scheduledInterviews;
    private String companyName;
    private Company company;

    public JobPosting(String jobTitle, String jobDescription, List<String> requiredSkills, int numberOfSlots, double packageOffered,int CGPACriteria, Company company) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.requiredSkills = requiredSkills;
        this.numberOfSlots = numberOfSlots;
        this.packageOffered = packageOffered;
        this.CGPACriteria = CGPACriteria;
        this.scheduledInterviews = new ArrayList<>();
        this.company = company;

    }

    public JobPosting(String companyName, double packageOffered, List<String> requiredSkills, String jobTitle){
        this.companyName = companyName;
        this.packageOffered = packageOffered;
        this.requiredSkills = requiredSkills;
        this.jobTitle = jobTitle;
    }

    public JobPosting(String companyName, double packageOffered, String jobDescription, List<String> requiredSkills, String jobTitle) {
        this.companyName = companyName;
        this.packageOffered = packageOffered;
        this.requiredSkills = requiredSkills;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
    }


    public String getJobTitle() {
        return jobTitle;
    }
    public String getJobDescription() {
        return jobDescription;
    }
    public List<String> getRequiredSkills() {
        return requiredSkills;
    }
    public int getNumberOfSlots() {
        return numberOfSlots;
    }
    public double getPackageOffered() {
        return packageOffered;
    }
    public Company getCompany(){
        return company;
    }
    public List<Interview> getScheduledInterviews() {
        return scheduledInterviews;
    }
    public int getCGPACriteria(){
        return CGPACriteria;
    }

    @Override
    public String toString() {
        return "Job Title: " + jobTitle + "\n" +
                "Job Description: " + jobDescription + "\n" +
                "Required Skills: " + String.join(", ", requiredSkills) + "\n" +
                "Package Offered: " + packageOffered;
    }
    public void scheduleInterview(Interview interview) {
        this.scheduledInterviews.add(interview);
    }
}
