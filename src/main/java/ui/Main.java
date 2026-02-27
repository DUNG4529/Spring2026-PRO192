
package ui;

import manager.HrManager;
import java.util.Scanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import utils.Validation;
import Service.AttendanceService;
import Service.ReportService;
import Service.SalaryService;
import Service.EmployeeService;
import java.util.List;


public class Main {

    // Scanner instance
    private static final Scanner sc = new Scanner(System.in);

    // Check valid ID
    public static String checkID(String prompt) {
        String id;
        while (true) {
            System.out.print(prompt);
            id = sc.nextLine();
            // Validate ID
            if (Validation.validID(id)) {
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

    // valid date input
    public static String checkDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateInput;
        while (true) {
            System.out.print(prompt);
            dateInput = sc.nextLine();
            try {
                LocalDate.parse(dateInput, formatter);
                return dateInput;
            } catch (DateTimeParseException e) {
                System.out.println("Date not valid! Please enter again.");
            }
        }
    }

    public static void main(String[] args) {
        // Khởi tạo đối tượng quản lý và Scanner
        HrManager hrManager = new HrManager();
        int choice = 0;

        // --------------------------------------------------------
        // MAIN MENU (Menu chính điều hướng các chức năng - CT: Main Menu)
        // --------------------------------------------------------
        do {
            System.out.println("======================================");
            System.out.println("      HUMAN RESOURCE MANAGEMENT       ");
            System.out.println("======================================");
            System.out.println("1. Manage Employees");
            System.out.println("2. Attendance Management");
            System.out.println("3. Salary Management");
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            System.out.println("--------------------------------------");
            System.out.print("Choose an option: ");
            
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1; // Bắt lỗi nếu người dùng nhập chữ thay vì số
            }

            // Xử lý điều hướng dựa trên lựa chọn
            switch (choice) {
                case 1:
                    manageEmployeesMenu(hrManager);
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
    // --------------------------------------------------------
    // SUB-MENUS (Các Menu phụ phân rã theo chức năng - CT: Sub-Menu)
    // --------------------------------------------------------
    
    // Menu quản lý nhân viên (Employee Management) // BR1, BR2, BR4, BR5, BR6
    private static void manageEmployeesMenu(HrManager hrManager) {
        System.out.println("\n----------- EMPLOYEE MANAGEMENT -----------");
        System.out.println("1. Add new employee");
        System.out.println("2. Update employee information");
        System.out.println("3. Remove an employee");
        System.out.println("4. View all employees");
        System.out.println("5. Search employees by name, department, or job title");
        System.out.println("6. Back to Main Menu");
        
        int choice = 0;

        // Calling corresponding functions from hrManager for employee management
        do {
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1; // Bắt lỗi nếu người dùng nhập chữ thay vì số
            }

            switch (choice) {
                case 1:
                    // hrManager.addEmployee(...);
                    break;
                case 2:
                    // hrManager.updateEmployee(...);
                    break;
                case 3:
                    // hrManager.deleteEmployeeById(...);
                    break;
                case 4:
                    // hrManager.viewAllEmployees();
                    break;
                case 5:
                    // hrManager.searchEmployees(...);
                    break;
                case 6:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose from 1 to 6.");
            }
        } while (choice != 6);
    }
    // Menu quản lý chấm công (Attendance Management) // BR3
    private static void attendanceManagementMenu(HrManager hrManager) {
        System.out.println("\n----------- ATTENDANCE MANAGEMENT -----------");
        System.out.println("1. Record daily attendance");
        System.out.println("2. Update attendance record");
        System.out.println("3. View attendance history");
        System.out.println("4. Calculate total working days and absences");
        System.out.println("5. Back to Main Menu");
        System.out.print("Choose an option: ");

        // Calling corresponding functions from hrManager for attendance management
    }

    // Menu quản lý lương (Salary Management) // BR7, BR8, BR9
    private static void salaryManagementMenu(HrManager hrManager) {
        System.out.println("\n----------- SALARY MANAGEMENT -----------");
        System.out.println("1. Calculate monthly salary for an employee");
        System.out.println("2. View salary details");
        System.out.println("3. Generate salary report for all employees");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose an option: ");

        // Calling corresponding functions from hrManager for salary management
    }
    
    // Menu báo cáo (Reports) // BR8
    private static void reportsMenu(HrManager hrManager) {
        System.out.println("\n----------- REPORTS -----------");
        System.out.println("1. List employees with low attendance");
        System.out.println("2. List highest-paid employees");
        System.out.println("3. Back to Main Menu");
        System.out.print("Choose an option: ");
        
        // Calling corresponding functions from hrManager for reports
    }
}