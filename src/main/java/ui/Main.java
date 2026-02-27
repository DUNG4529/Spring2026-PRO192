
package ui;

import manager.HRManager;
import entity.Attendance;
import entity.Employee;
import entity.FullTimeEmployee;
import entity.PartTimeEmployee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import utils.Validation;


public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Check valid ID
    public static String checkID(String prompt) {
        String id;
        while (true) {
            System.out.print(prompt);
            id = sc.nextLine();
            // Accept both formats: XX000000 and E01-style demo IDs
            if (Validation.validID(id) || id.matches("^E\\d{2}$")) {
                return id;
            }
            // Invalid ID message
            System.out.println("ID not valid! Please enter again.");
        }
    }

    // check valid Name
    public static String checkName(String prompt) {
        String name;
        while (true) {
            System.out.print(prompt);
            name = sc.nextLine();
            // Validate Name
            if (Validation.validName(name)) {
                return name;
            }
            // Invalid Name message
            System.out.println("Name not valid! Please enter again.");
        }
    }

    // check valid String
    public static String checkString(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = sc.nextLine();
            // Validate String
            if (Validation.validString(input)) {
                return input;
            }
            // Invalid String message
            System.out.println("Input not valid! Please enter again.");
        }
    }

    // valid number input
    public static double checkDouble(String prompt) {
        double number;
        while (true) {
            System.out.print(prompt);
            try {
                number = Double.parseDouble(sc.nextLine());
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number! Please enter again.");
            }
        }
    }

    // valid salary input
    public static double checkSalary(String prompt) {
        double salary;
        while (true) {
            System.out.print(prompt);
            salary = Double.parseDouble(sc.nextLine());
            // Validate Salary
            if (Validation.validSalary(salary)) {
                return salary;
            }
            // Invalid Salary message
            System.out.println("Salary not valid! Please enter again.");
        }
    }

    public static void main(String[] args) {
        HRManager hrManager = new HRManager();
        seedData(hrManager);

        int choice;
        do {
            System.out.println("======================================");
            System.out.println("      HUMAN RESOURCE MANAGEMENT       ");
            System.out.println("======================================");
            System.out.println("1. Employee Management");
            System.out.println("2. Attendance Management");
            System.out.println("3. Salary Management");
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            System.out.println("--------------------------------------");
            System.out.print("Choose an option: ");

            choice = readInt();
            switch (choice) {
                case 1:
                    employeeManagementMenu(hrManager);
                    break;
                case 2:
                    attendanceManagementMenu(hrManager);
                    break;
                case 3:
                    salaryManagementMenu(hrManager);
                    break;
                case 4:
                    reportsMenu(hrManager);
                    break;
                case 5:
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose from 1 to 5.");
            }
        } while (choice != 5);

        sc.close();
    }

    private static void employeeManagementMenu(HRManager hrManager) {
        int choice;
        do {
            System.out.println("\n----------- EMPLOYEE MANAGEMENT -----------");
            System.out.println("Task B3 - View All Employees");
            System.out.println("1. View all employees");
            System.out.println("0. Back to main menu");
            System.out.print("Choose an option: ");
            choice = readInt();

            switch (choice) {
                case 1:
                    viewAllEmployees(hrManager);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void viewAllEmployees(HRManager hrManager) {
        List<Employee> employees = hrManager.getAllEmployees();

        System.out.println("\nTask B3 — View All Employees");
        System.out.println("---------------- EMPLOYEE LIST ----------------");
        System.out.printf("%-8s %-20s %-15s %-18s %-15s%n", "ID", "Name", "Department", "Job Title", "Salary");
        System.out.println("--------------------------------------------------------------------------");

        for (Employee employee : employees) {
            System.out.printf(
                    "%-8s %-20s %-15s %-18s %,15.0f%n",
                    employee.getId(),
                    employee.getName(),
                    employee.getDepartment(),
                    employee.getJobTitle(),
                    employee.getBaseSalary());
        }

        pressEnterToReturn();
    }

    private static void attendanceManagementMenu(HRManager hrManager) {
        int choice;
        do {
            System.out.println("\n----------- ATTENDANCE MANAGEMENT -----------");
            System.out.println("Task B4 - Record Attendance");
            System.out.println("1. Record attendance");
            System.out.println("Task B5 - View Attendance History");
            System.out.println("2. View attendance history");
            System.out.println("0. Back to main menu");
            System.out.print("Choose an option: ");
            choice = readInt();

            switch (choice) {
                case 1:
                    recordAttendance(hrManager);
                    break;
                case 2:
                    viewAttendanceHistory(hrManager);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void recordAttendance(HRManager hrManager) {
        System.out.println("\nTask B4 — Record Attendance");
        System.out.println("---------- RECORD ATTENDANCE ----------");
        String id = checkID("Employee ID: ");
        Employee employee = hrManager.findEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            pressEnterToReturn();
            return;
        }

        LocalDate date = readDate("Date (dd/MM/yyyy): ");
        double overtime = checkDouble("Overtime Hours: ");

        System.out.println("Status: Present");

        Attendance attendance = new Attendance(id, date, Attendance.AttendanceStatus.PRESENT, overtime);

        try {
            hrManager.addAttendance(attendance);
            System.out.println("Output");
            System.out.println("Attendance recorded successfully.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Output");
            System.out.println(ex.getMessage());
        }

        pressEnterToReturn();
    }

    private static void viewAttendanceHistory(HRManager hrManager) {
        System.out.println("\nTask B5 — View Attendance History");
        String id = checkID("Employee ID: ");
        List<Attendance> history = hrManager.getAttendanceByEmployeeId(id);
        if (history == null) {
            history = new ArrayList<>();
        }

        System.out.println("\nEmployee ID: " + id);
        System.out.println("----------------------------------------");
        System.out.printf("%-12s %-10s %-10s%n", "Date", "Status", "Overtime");
        System.out.println("----------------------------------------");

        if (history.isEmpty()) {
            System.out.println("No attendance history found.");
            pressEnterToReturn();
            return;
        }

        for (Attendance attendance : history) {
            String dateText = attendance.getDate().format(DATE_FORMATTER);
            System.out.printf("%-12s %-10s %-10.0f%n",
                    dateText,
                    attendance.getStatus().getDisplayName(),
                    attendance.getOvertime());
        }

        pressEnterToReturn();
    }

    private static void salaryManagementMenu(HRManager hrManager) {
        int choice;
        do {
            System.out.println("\n----------- SALARY MANAGEMENT -----------");
            System.out.println("Task B6 - Calculate Salary");
            System.out.println("1. Calculate salary");
            System.out.println("0. Back to main menu");
            System.out.print("Choose an option: ");
            choice = readInt();

            switch (choice) {
                case 1:
                    calculateSalary(hrManager);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void calculateSalary(HRManager hrManager) {
        System.out.println("\nTask B6 — Calculate Salary");
        System.out.println("---------- CALCULATE SALARY ----------");
        String id = checkID("Employee ID: ");
        Employee employee = hrManager.findEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            pressEnterToReturn();
            return;
        }

        int month = readIntInRange("Month (1-12): ", 1, 12);
        int year = readIntInRange("Year: ", 2000, 3000);

        System.out.printf("Employee ID: %s%n", id);
        System.out.printf("Month / Year: %d / %d%n", month, year);

        List<Attendance> history = hrManager.getAttendanceByEmployeeId(id);
        int workingDays = 0;
        int absenceDays = 0;
        int overtimeHours = 0;

        for (Attendance attendance : history) {
            if (attendance.getDate().getMonthValue() == month && attendance.getDate().getYear() == year) {
                if (attendance.getStatus() == Attendance.AttendanceStatus.PRESENT) {
                    workingDays++;
                } else {
                    absenceDays++;
                }
                overtimeHours += (int) attendance.getOvertime();
            }
        }

        double totalSalary = hrManager.calculateSalaryById(id, month, year);

        System.out.println("Output");
        System.out.println("Salary calculated successfully.");
        System.out.println("Total Working Days: " + workingDays);
        System.out.println("Overtime Hours: " + overtimeHours);
        System.out.println("Absence Days: " + absenceDays);
        System.out.printf("Total Salary: %,.0f VND%n", totalSalary);

        pressEnterToReturn();
    }

    private static void reportsMenu(HRManager hrManager) {
        int choice;
        do {
            System.out.println("\nREPORTS");
            System.out.println("Task B7 - Employees with Low Attendance");
            System.out.println("1. Low attendance report");
            System.out.println("Task B8 - Highest Paid Employees");
            System.out.println("2. Highest paid employees report");
            System.out.println("0. Back to main menu");
            System.out.print("Choose an option: ");
            choice = readInt();

            switch (choice) {
                case 1:
                    System.out.println("\nTask B7 — Employees with Low Attendance");
                    int threshold = readIntInRange("Absent-day threshold: ", 1, 31);
                    hrManager.reportLowAttendance(threshold);
                    pressEnterToReturn();
                    break;
                case 2:
                    System.out.println("\nTask B8 — Highest Paid Employees");
                    hrManager.reportHighestPaid();
                    pressEnterToReturn();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static int readInt() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            int value = readInt();
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Invalid input. Please enter a number from " + min + " to " + max + ".");
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            String dateText = checkString(prompt);
            try {
                return LocalDate.parse(dateText, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Date not valid! Please enter again.");
            }
        }
    }

    private static void pressEnterToReturn() {
        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }

    private static void seedData(HRManager hrManager) {
        try {
            hrManager.addEmployee(new FullTimeEmployee("E01", "Nguyen Van An", "R&D", 12000000,
                    "Senior Engineer", "01/01/2024", Employee.Status.ACTIVE));
            hrManager.addEmployee(new PartTimeEmployee("E02", "Tran Thi Hoa", "HR", 10000000,
                    "HR Officer", "01/03/2024", Employee.Status.ACTIVE));
            hrManager.addEmployee(new FullTimeEmployee("E03", "Le Van Binh", "Sales", 15000000,
                    "Sales Manager", "01/02/2024", Employee.Status.ACTIVE));
            hrManager.addEmployee(new PartTimeEmployee("E04", "Pham Thi Mai", "Marketing", 9000000,
                    "Marketing Specialist", "01/04/2024", Employee.Status.ACTIVE));
            hrManager.addEmployee(new FullTimeEmployee("E05", "Hoang Van Cuong", "IT", 13000000,
                    "IT Support", "01/01/2024", Employee.Status.ACTIVE));
        } catch (IllegalArgumentException ignored) {
        }
    }
}