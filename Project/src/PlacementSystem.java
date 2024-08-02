import java.util.*;
import java.util.stream.Collectors;

public class PlacementSystem extends Exception {
    private List<JobPosting> jobPostings;
    public Stack<JobPosting> recentJobPostings;
    private List<Student> students;
    private PriorityQueue<Student> studentQueue;
    private Queue<Interview> interviewQueue;
    private Company[] previousYearCompanies;

    public PlacementSystem() {
        this.jobPostings = new ArrayList<>();
        this.recentJobPostings = new Stack<>();
        this.students = new ArrayList<>();
        this.studentQueue = new PriorityQueue<>();
        this.interviewQueue = new LinkedList<>();
        this.previousYearCompanies = new Company[100]; // Assuming a max of 100 companies visited last year
        initializePreviousYearCompanies();
    }

    private void initializePreviousYearCompanies() {
        // Example companies with package offered
        // Initialize example companies visited last year
        previousYearCompanies[0] = new Company("Google", 1200000, new Date(), Arrays.asList("Java", "Python", "Machine Learning"), "Software Engineer");
        previousYearCompanies[1] = new Company("Microsoft", 1100000, new Date(), Arrays.asList("C++", "C#", "Web Development"), "Software Developer");
        previousYearCompanies[2] = new Company("Amazon", 1150000, new Date(), Arrays.asList("Java", "Cloud Computing", "DevOps"), "Cloud Architect");
        previousYearCompanies[3] = new Company("Apple", 1250000, new Date(), Arrays.asList("Swift", "iOS Development", "Objective-C"), "iOS Developer");
        previousYearCompanies[4] = new Company("Facebook", 1180000, new Date(), Arrays.asList("Python", "Data Analysis", "Social Media"), "Data Scientist");
        previousYearCompanies[5] = new Company("Netflix", 1120000, new Date(), Arrays.asList("Java", "Scala", "Streaming"), "Backend Developer");
        previousYearCompanies[6] = new Company("Tesla", 1300000, new Date(), Arrays.asList("Embedded Systems", "AI", "Electric Vehicles"), "Embedded Software Engineer");
        previousYearCompanies[7] = new Company("Uber", 1050000, new Date(), Arrays.asList("Python", "Node.js", "Mobile Development"), "Full Stack Developer");
        previousYearCompanies[8] = new Company("Airbnb", 1080000, new Date(), Arrays.asList("React", "JavaScript", "Frontend Development"), "Frontend Developer");
        previousYearCompanies[9] = new Company("IBM", 1000000, new Date(), Arrays.asList("Java", "Mainframe", "Artificial Intelligence"), "Software Engineer");
        previousYearCompanies[10] = new Company("Intel", 980000, new Date(), Arrays.asList("C", "C++", "Hardware Design"), "Hardware Engineer");
        previousYearCompanies[11] = new Company("Oracle", 1020000, new Date(), Arrays.asList("Java", "SQL", "Database Management"), "Database Administrator");
        previousYearCompanies[12] = new Company("Salesforce", 1100000, new Date(), Arrays.asList("Salesforce CRM", "Apex", "Cloud Integration"), "Salesforce Developer");
        previousYearCompanies[13] = new Company("Adobe", 1150000, new Date(), Arrays.asList("JavaScript", "UI/UX Design", "Adobe Creative Suite"), "UI/UX Designer");
        previousYearCompanies[14] = new Company("Cisco", 980000, new Date(), Arrays.asList("Networking", "Security", "Cisco Products"), "Network Engineer");
        previousYearCompanies[15] = new Company("VMware", 1050000, new Date(), Arrays.asList("Virtualization", "Cloud Computing", "VMware Products"), "Cloud Engineer");
        previousYearCompanies[16] = new Company("PayPal", 1080000, new Date(), Arrays.asList("Java", "Python", "Payment Systems"), "Software Engineer");
        previousYearCompanies[17] = new Company("HP Inc.", 950000, new Date(), Arrays.asList("Printing Technology", "IoT", "Hardware Development"), "Hardware Engineer");
        previousYearCompanies[18] = new Company("Nvidia", 1150000, new Date(), Arrays.asList("CUDA", "Graphics Processing", "AI"), "GPU Engineer");
        previousYearCompanies[19] = new Company("Sony", 1000000, new Date(), Arrays.asList("Game Development", "C#", "PlayStation"), "Game Developer");

    }
    // Method to post a new job
    public void postJob(JobPosting job) {
        jobPostings.add(job);
        recentJobPostings.push(job);
        System.out.println("Job posted successfully: " + job.getJobTitle());
    }

    // Method to register a student
    public void registerStudent(Student student) {
        students.add(student);
        studentQueue.add(student);
        System.out.println("Student registered successfully: " + student.getName());
    }

    // Method to filter eligible students for each job posting and schedule interviews
    public void filterEligibleStudentsAndScheduleInterviews() {
        for (JobPosting job : jobPostings) {
            System.out.println("Job Title: " + job.getJobTitle());
            System.out.println("Eligible Students:");

            int availableSlots = job.getNumberOfSlots();
            int count = 0;

            for (Student student : students) {
                if (count < availableSlots && isStudentEligible(student, job)) {
                    System.out.println("- " + student.getName());
                    // Schedule interview with a starting date (for example, today)
                    scheduleInterview(student, job.getCompany(), new Date());
                    count++;
                }
            }

            System.out.println();
        }
    }

    // Helper method to check if a student is eligible for a job posting
    private boolean isStudentEligible(Student student, JobPosting job) {
        // Check if student's skills match required skills
        Set<String> requiredSkills = new HashSet<>(job.getRequiredSkills());
        Set<String> studentSkills = new HashSet<>(student.getSkills());

        boolean skillsMatch = studentSkills.containsAll(requiredSkills);

        // Check if student's quiz marks are sufficient
        int requiredQuizMarks = 70; // Example threshold for quiz marks
        boolean sufficientQuizMarks = student.getQuizMarks() >= requiredQuizMarks;

        return skillsMatch && sufficientQuizMarks;
    }

    // Method to schedule an interview for a student with a job posting
    private void scheduleInterview(Student student, Company company, Date interviewDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(interviewDate);

        boolean conflictDetected = true;

        while (conflictDetected) {
            conflictDetected = false;
            for (InterviewSchedule schedule : student.getInterviewSchedules()) {
                if (schedule.getInterviewDate().equals(calendar.getTime())) {
                    conflictDetected = true;
                    calendar.add(Calendar.DAY_OF_YEAR, 1); // Move to the next day
                    break;
                }
            }
        }

        // Schedule interview on the next available date
        Date scheduledDate = calendar.getTime();
        student.addInterviewSchedule(new InterviewSchedule(company, scheduledDate));
        interviewQueue.add(new Interview(student, new JobPosting(company.getName(), company.getRole(), company.getRequiredSkills(), 0, company.getPackageOffered(), company), scheduledDate));
        System.out.println("Interview scheduled for student " + student.getName() + " with company " + company.getName() + " on " + scheduledDate);
    }

    public List<String> filterByPackage(double minPackage) {
        return Arrays.stream(previousYearCompanies)
                .filter(company -> company != null && company.getPackageOffered() >= minPackage)
                .map(Company::getName)
                .collect(Collectors.toList());
    }

    public List<String> filterBySkills(List<String> requiredSkills) {
        return Arrays.stream(previousYearCompanies)
                .filter(company -> company != null && company.getRequiredSkills().containsAll(requiredSkills))
                .map(Company::getName)
                .collect(Collectors.toList());
    }

    public List<String> filterByPackageAndSkills(double minPackage, List<String> requiredSkills) {
        return Arrays.stream(previousYearCompanies)
                .filter(company -> company != null && company.getPackageOffered() >= minPackage)
                .filter(company -> company.getRequiredSkills().containsAll(requiredSkills))
                .map(Company::getName)
                .collect(Collectors.toList());
    }

    // Method to sort companies based on package or specific skills or both
    public void sortCompanies(int sortBy) {
        Scanner sc = new Scanner(System.in);
        switch (sortBy) {
            case 1:
                System.out.print("Enter the minimum package: ");
                double minPackage = sc.nextInt();
                System.out.println();
                System.out.print("The companies are: ");
                System.out.print(filterByPackage(minPackage));
                System.out.println();
                break;
            case 2:
                System.out.print("Enter the skills (comma separated): ");
                String skills = sc.nextLine();
                List<String> listOfSkills = new ArrayList<>(Arrays.asList(skills.split(",")));
                System.out.println();
                System.out.print("The companies are: ");
                System.out.print(filterBySkills(listOfSkills));
                System.out.println();
                break;
            case 3:
                System.out.print("Enter the skills (comma separated): ");
                String skillsToSort = sc.nextLine();
                System.out.print("Enter the minimum package: ");
                double minimumPack = sc.nextInt();
                List<String> SkillsList = new ArrayList<>(Arrays.asList(skillsToSort.split(",")));
                System.out.println();
                System.out.print("The companies are: ");
                System.out.print(filterByPackageAndSkills(minimumPack, SkillsList));
                System.out.println();
                break;
            default:
                System.out.println("Invalid sort option.");
                break;
        }
    }

    public void displayScheduledInterviews() {
        System.out.println("Scheduled Interviews:");
        while (!interviewQueue.isEmpty()) {
            Interview interview = interviewQueue.poll();
            System.out.println("Student: " + interview.getStudent().getName() +
                    ", Company: " + interview.getJobPosting().getCompany().getName() +
                    ", Role: " + interview.getJobPosting().getJobTitle() +
                    ", Date: " + interview.getInterviewDate());
        }
    }

    // Method to search for a package offered by a company based on previous year's data
    public double searchPackageByCompanyName(String companyName) {
        for (Company company : previousYearCompanies) {
            if (company != null && company.getName().equals(companyName)) {
                return company.getPackageOffered();
            }
        }
        return -1; // Company not found
    }

    public List<String> searchStacksByCompanyName(String companyName) {
        for (Company company : previousYearCompanies) {
            if (company != null && company.getName().equals(companyName)) {
                return company.getRequiredSkills();
            }
        }
        return new ArrayList<>(); // Company not found
    }
    public String searchRoleByCompanyName(String companyName) {
        for (Company company : previousYearCompanies) {
            if (company != null && company.getName().equals(companyName)) {
                return company.getRole();
            }
        }
        return " "; // Company not found
    }
}