import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;
        PlacementSystem placementSystem = null;
        try {
            connection = DatabaseConnection.getConnection();
            placementSystem = new PlacementSystem(connection);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return;
        }

        System.out.println("\n");
        System.out.println("WELCOME TO PLACEMENT SCREENING SYSTEM");
        System.out.println("-------------------------------------");

        // Main menu loop
        while (true) {
            System.out.println("\nSelect User Type:");
            System.out.println("1. Company");
            System.out.println("2. Student");
            System.out.println("3. Administrator");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int userType = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (userType) {
                case 1:
                    // Company actions (e.g., post job)
                    CompanyPage companyPage = new CompanyPage();
                    companyPage.display();
                    break;
                case 2:
                    // Student actions (e.g., register, apply for jobs)
                    StudentPage studentPage = new StudentPage();
                    studentPage.display();
                    break;
                case 3:
                    // Administrator actions (admin functions)
                    AdminPage adminPage = new AdminPage(placementSystem);
                    adminPage.display();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    try {
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Error closing database connection: " + e.getMessage());
                    }
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}