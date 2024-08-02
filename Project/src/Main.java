import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        PlacementSystem placementSystem = new PlacementSystem();

        System.out.println("\n");
        System.out.println("WELCOME TO PLACEMENT SCREENING SYSTEM");
        System.out.println("-------------------------------------");
        System.out.println("\t \t \t DASHBOARD");
        while (true) {
            System.out.println("\n1. Post Job");
            System.out.println("2. Register Student");
            System.out.println("3. Filter Eligible Students and Schedule Interviews");
            System.out.println("4. View Recent Job Postings");
            System.out.println("5. Sort Companies");
            System.out.println("6. Search for Company Package");
            System.out.println("7. Display interview schedule if scheduled");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice){
                case 1:
                    System.out.print("Enter Company Name: ");
                    String jobTitle = scanner.nextLine();
                    System.out.print("Enter Job Description: ");
                    String jobDescription = scanner.nextLine();
                    System.out.print("Enter Required Skills (comma separated): ");
                    List<String> requiredSkills = Arrays.asList(scanner.nextLine().split(","));
                    System.out.print("Enter Number of Slots: ");
                    int numberOfSlots = scanner.nextInt();
                    System.out.print("Enter Package Offered: ");
                    double packageOffered = scanner.nextDouble();
                    System.out.print("Visiting Date (YYYY-MM-DD): ");
                    String visitingDateString = scanner.next();
                    Date visitingDate;
                    try {
                        visitingDate = new SimpleDateFormat("yyyy-MM-dd").parse(visitingDateString);
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
                        return;
                    }
                    JobPosting jobPosting = new JobPosting(jobTitle, jobDescription, requiredSkills, numberOfSlots, packageOffered, new Company(jobTitle, packageOffered, visitingDate, requiredSkills, jobDescription));
                    placementSystem.postJob(jobPosting);
                    break;

                case 2:
                    System.out.print("Enter Student Name: ");
                    String studentName = scanner.nextLine();
                    System.out.print("Enter Register Number: ");
                    String registerNumber = scanner.nextLine();
                    System.out.print("Enter CGPA: ");
                    double CGPA = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Skills (comma separated): ");
                    List<String> skills = Arrays.asList(scanner.nextLine().split(","));
                    System.out.print("Enter Quiz Marks: ");
                    int quizMarks = scanner.nextInt();
                    Student student = new Student(studentName, registerNumber, CGPA, skills, quizMarks);
                    placementSystem.registerStudent(student);
                    break;

                case 3:
                    placementSystem.filterEligibleStudentsAndScheduleInterviews();
                    break;

                case 4:
                    System.out.println("Recent Job Postings:");
                    while (!placementSystem.recentJobPostings.isEmpty()) {
                        JobPosting recentJob = placementSystem.recentJobPostings.pop();
                        System.out.println("- " + recentJob.getJobTitle());
                    }
                    break;

                case 5:
                    System.out.println();
                    System.out.println("Sort by \n1. Package \n2. Skills \n3. Package and Skills");
                    int sortBy = scanner.nextInt();
                    placementSystem.sortCompanies(sortBy);
                    break;

                case 6:
                    System.out.println();
                    System.out.print("Enter Company Name to Search: ");
                    String companyName = scanner.nextLine();
                    double companyPackage = placementSystem.searchPackageByCompanyName(companyName);
                    List<String> techStacks = placementSystem.searchStacksByCompanyName(companyName);
                    String role = placementSystem.searchRoleByCompanyName(companyName);
                    if (companyPackage != -1) {
                        System.out.println("Role in " + companyName + ": " + role);
                        System.out.println("Package offered by " + companyName + ": " + companyPackage);
                        System.out.println("Tech Stacks required for " + companyName + ": " + techStacks);
                    } else {
                        System.out.println("Company not found.");
                    }
                    break;
                case 7:
                    System.out.println();
                    placementSystem.displayScheduledInterviews();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}