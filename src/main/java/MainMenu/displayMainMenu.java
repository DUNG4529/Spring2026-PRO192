package MainMenu;



// import java Scanner;
import java.util.Scanner;

public class displayMainMenu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Employee Management System =====");
        System.out.println("1. Manage Employees");
        System.out.println("2. Manage Attendance");
        System.out.println("3. Calculate Salaries");
        System.out.println("4. Exit");
        System.out.print("Please select an option (1-4): ");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                System.out.println("Managing Employees...");
                // Call employee management functions here
                break;
            case 2:
                System.out.println("Managing Attendance...");
                // Call attendance management functions here
                break;
            case 3:
                System.out.println("Calculating Salaries...");
                // Call salary calculation functions here
                break;
            case 4:
                System.out.println("Exiting the system. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
        }

        scanner.close();
    }
}
