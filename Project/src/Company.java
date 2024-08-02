import java.util.*;
class Company {
    private String name;
    private double packageOffered;
    private Date dateOfVisit;
    private List<String> requiredSkills;
    private String role;

    public Company(Date dateOfVisit){
        this.dateOfVisit = dateOfVisit;
    }
    public Company(String name, double packageOffered, Date dateOfVisit, List<String> requiredSkills, String role) {
        this.name = name;
        this.packageOffered = packageOffered;
        this.dateOfVisit = dateOfVisit;
        this.requiredSkills = requiredSkills;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public double getPackageOffered() {
        return packageOffered;
    }

    public Date getDateOfVisit() {
        return dateOfVisit;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public String getRole() {
        return role;
    }
}

