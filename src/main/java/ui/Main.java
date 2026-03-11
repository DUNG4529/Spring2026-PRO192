package ui;

import manager.HRManager;
import entity.*;
import java.util.*;
import java.time.*;

public class Main {
    private static final Scanner KEYBOARD_SCANNER = new Scanner(System.in);
    private static final String DATA_DIR = "data";

    public static void main(String[] args) {
        HRManager hrManager = new HRManager();
        hrManager.loadDataFromFiles(DATA_DIR);
        hrManager.initializeAttendance();

        while (true) {
            printMainMenu();
            String choice = KEYBOARD_SCANNER.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        manageEmployeesMenu(hrManager);
                        break;
                    case "2":
                        attendanceManagementMenu(hrManager);
                        break;
                    case "3":
                        salaryManagementMenu(hrManager);
                        break;
                    case "4":
                        reportsMenu(hrManager);
                        break;
                    case "5":
                        hrManager.saveDataToFiles(DATA_DIR);
                        System.out.println("Bye.");
                        KEYBOARD_SCANNER.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please select 1-5.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n================= MAIN MENU =================");
        System.out.println("        HUMAN RESOURCE MANAGEMENT");
        System.out.println("=============================================");
        System.out.println("1. Manage Employees");
        System.out.println("2. Attendance Management");
        System.out.println("3. Salary Management");
        System.out.println("4. Reports");
        System.out.println("5. Exit");
        System.out.println("---------------------------------------------");
        System.out.print("Choose: ");
    }

    private static void showAllEmployees(HRManager hrManager) {
        List<Employee> employees = hrManager.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees.");
            System.out.print("Press ENTER to return...");
            KEYBOARD_SCANNER.nextLine();
            return;
        }

        System.out.println("\n================ EMPLOYEE LIST =================");
        System.out.println();
        System.out.printf("%-6s %-16s %-12s %-18s %s%n", "ID", "Name", "Department", "Job Title", "Salary");
        System.out.println("--------------------------------------------------------------");
        for (Employee employee : employees) {
            System.out.printf("%-6s %-16s %-12s %-18s %,.0f%n",
                    employee.getId(),
                    employee.getName(),
                    employee.getDepartment(),
                    employee.getJobTitle(),
                    employee.getBaseSalary());
        }
        System.out.println();
        System.out.println("--------------------------------------------------------------");
        System.out.print("Press ENTER to return...");
        KEYBOARD_SCANNER.nextLine();
    }

    private static void manageEmployeesMenu(HRManager hrManager) {
        while (true) {
            System.out.println("\n----- MANAGE EMPLOYEES -----");
            System.out.println("1. Show all employees");
            System.out.println("2. Add employee");
            System.out.println("3. Update employee");
            System.out.println("4. Back");
            System.out.print("Choose: ");
            String choice = KEYBOARD_SCANNER.nextLine().trim();

            if ("1".equals(choice)) {
                showAllEmployees(hrManager);
            } else if ("2".equals(choice)) {
                addEmployee(hrManager);
            } else if ("3".equals(choice)) {
                updateEmployee(hrManager);
            } else if ("4".equals(choice)) {
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void attendanceManagementMenu(HRManager hrManager) {
        while (true) {
            System.out.println("\n----- ATTENDANCE MANAGEMENT -----");
            System.out.println("1. Mark attendance for today");
            System.out.println("2. Record attendance");
            System.out.println("3. View attendance history");
            System.out.println("4. Show all attendance");
            System.out.println("5. Back");
            System.out.print("Choose: ");
            String choice = KEYBOARD_SCANNER.nextLine().trim();

            if ("1".equals(choice)) {
                markTodayAttendance(hrManager);
            } else if ("2".equals(choice)) {
                recordAttendance(hrManager);
            } else if ("3".equals(choice)) {
                viewAttendanceHistory(hrManager);
            } else if ("4".equals(choice)) {
                System.out.println(hrManager.showAllAttendance());
            } else if ("5".equals(choice)) {
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void recordAttendance(HRManager hrManager) {
        System.out.println("\n----------- RECORD ATTENDANCE -----------");
        System.out.print("Employee ID      : ");
        String employeeId = KEYBOARD_SCANNER.nextLine().trim();

        Employee employee = hrManager.findEmployeeById(employeeId);
        if (employee == null) {
            System.out.println("Error: Employee not found");
            return;
        }

        System.out.print("Date             : ");
        String dateInput = KEYBOARD_SCANNER.nextLine().trim();
        LocalDate date = LocalDate.parse(dateInput, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println("Status           : 1.Present  2.Absent  3.Leave");
        System.out.print("Choose           : ");
        String statusChoice = KEYBOARD_SCANNER.nextLine().trim();

        Attendance.AttendanceStatus status;
        switch (statusChoice) {
            case "1":
                status = Attendance.AttendanceStatus.PRESENT;
                break;
            case "2":
                status = Attendance.AttendanceStatus.ABSENT;
                break;
            case "3":
                status = Attendance.AttendanceStatus.LEAVE;
                break;
            default:
                throw new IllegalArgumentException("Invalid status choice");
        }

        System.out.print("Overtime Hours   : ");
        double overtimeHours = Double.parseDouble(KEYBOARD_SCANNER.nextLine().trim());

        System.out.println();
        System.out.println("----------- RECORD ATTENDANCE -----------");
        System.out.println();
        System.out.println("Employee ID      : " + employeeId);
        System.out.println("Date             : " + date.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("Status           : " + status.getDisplayName());
        System.out.printf("Overtime Hours   : %.2f%n", overtimeHours);
        System.out.println();
        System.out.println("[1] Save");
        System.out.println("[2] Cancel");
        System.out.println("----------------------------------------");
        System.out.print("Select option: ");
        String option = KEYBOARD_SCANNER.nextLine().trim();

        if ("2".equals(option)) {
            System.out.println("Record cancelled.");
            return;
        }
        if (!"1".equals(option)) {
            throw new IllegalArgumentException("Invalid option");
        }

        Attendance attendance = new Attendance(employeeId, date, status, overtimeHours);
        hrManager.addAttendance(attendance);
        hrManager.saveDataToFiles(DATA_DIR);
        System.out.println("Attendance recorded successfully.");
    }

    private static void viewAttendanceHistory(HRManager hrManager) {
        System.out.println("\n----------- ATTENDANCE HISTORY -----------");
        System.out.println();
        System.out.print("Employee ID : ");
        String employeeId = KEYBOARD_SCANNER.nextLine().trim();

        Employee employee = hrManager.findEmployeeById(employeeId);
        if (employee == null) {
            System.out.println("Error: Employee not found");
            return;
        }

        List<Attendance> records = hrManager.getAttendanceByEmployeeId(employeeId);

        if (records.isEmpty()) {
            System.out.println("No attendance records found for this employee.");
            System.out.print("Press ENTER to return...");
            KEYBOARD_SCANNER.nextLine();
            return;
        }

        records.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        System.out.println();
        System.out.println("Date         Status      Overtime");
        System.out.println("----------------------------------");
        for (Attendance record : records) {
            System.out.printf("%-12s %-11s %s%n",
                    record.getDate().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    record.getStatus().getDisplayName(),
                    String.format("%.0f", record.getOvertime()));
        }
        System.out.println();
        System.out.println("----------------------------------");
        System.out.print("Press ENTER to return...");
        KEYBOARD_SCANNER.nextLine();
    }

    private static void salaryManagementMenu(HRManager hrManager) {
        while (true) {
            System.out.println("\n----- SALARY MANAGEMENT -----");
            System.out.println("1. Calculate salary");
            System.out.println("2. Back");
            System.out.print("Choose: ");
            String choice = KEYBOARD_SCANNER.nextLine().trim();

            if ("1".equals(choice)) {
                calculateSalary(hrManager);
            } else if ("2".equals(choice)) {
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void reportsMenu(HRManager hrManager) {
        while (true) {
            System.out.println("\n----- REPORTS -----");
            System.out.println("1. Low attendance report");
            System.out.println("2. Highest paid report");
            System.out.println("3. Back");
            System.out.print("Choose: ");
            String choice = KEYBOARD_SCANNER.nextLine().trim();

            if ("1".equals(choice)) {
                lowAttendanceReport(hrManager);
            } else if ("2".equals(choice)) {
                highestPaidReport(hrManager);
            } else if ("3".equals(choice)) {
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void highestPaidReport(HRManager hrManager) {
        System.out.print("Month / Year (e.g. 3 / 2026): ");
        String[] parts = KEYBOARD_SCANNER.nextLine().trim().split("/");
        int month = Integer.parseInt(parts[0].trim());
        int year = Integer.parseInt(parts[1].trim());

        List<Employee> employees = hrManager.getAllEmployees();

        double maxSalary = Double.NEGATIVE_INFINITY;
        for (Employee emp : employees) {
            if (emp.getStatus() != Employee.Status.ACTIVE) continue;
            double salary = hrManager.calculateSalaryById(emp.getId(), month, year);
            if (salary > maxSalary) maxSalary = salary;
        }

        System.out.println("\n------- HIGHEST PAID EMPLOYEES -------");
        System.out.println();
        System.out.printf("%-8s %-16s %s%n", "ID", "Name", "Total Salary");
        System.out.println("-----------------------------------");

        boolean found = false;
        if (maxSalary > Double.NEGATIVE_INFINITY) {
            for (Employee emp : employees) {
                if (emp.getStatus() != Employee.Status.ACTIVE) continue;
                double salary = hrManager.calculateSalaryById(emp.getId(), month, year);
                if (Math.abs(salary - maxSalary) < 0.0001) {
                    System.out.printf("%-8s %-16s %,.0f VND%n", emp.getId(), emp.getName(), salary);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No salary data found for this month.");
        }

        System.out.println();
        System.out.println("-----------------------------------");
        System.out.print("Press ENTER to return...");
        KEYBOARD_SCANNER.nextLine();
    }

    private static void lowAttendanceReport(HRManager hrManager) {
        System.out.print("Month / Year (e.g. 3 / 2026): ");
        String[] parts = KEYBOARD_SCANNER.nextLine().trim().split("/");
        int month = Integer.parseInt(parts[0].trim());
        int year = Integer.parseInt(parts[1].trim());

        System.out.print("Absent days threshold (default 3): ");
        String thresholdInput = KEYBOARD_SCANNER.nextLine().trim();
        int threshold = thresholdInput.isEmpty() ? 3 : Integer.parseInt(thresholdInput);

        List<Employee> employees = hrManager.getAllEmployees();

        System.out.println("\n-------- LOW ATTENDANCE REPORT --------");
        System.out.println();
        System.out.printf("%-8s %-16s %s%n", "ID", "Name", "Absent Days");
        System.out.println("-----------------------------------");

        boolean found = false;
        for (Employee emp : employees) {
            if (emp.getStatus() != Employee.Status.ACTIVE) continue;
            List<Attendance> records = hrManager.getAttendanceByEmployeeId(emp.getId());
            int absentDays = 0;
            for (Attendance a : records) {
                if (a.getDate().getMonthValue() == month && a.getDate().getYear() == year
                        && a.getStatus() == Attendance.AttendanceStatus.ABSENT) {
                    absentDays++;
                }
            }
            if (absentDays > threshold) {
                System.out.printf("%-8s %-16s %d%n", emp.getId(), emp.getName(), absentDays);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No employees with low attendance.");
        }

        System.out.println();
        System.out.println("-----------------------------------");
        System.out.print("Press ENTER to return...");
        KEYBOARD_SCANNER.nextLine();
    }

    private static void addEmployee(HRManager hrManager) {
        System.out.println("------------- ADD EMPLOYEE -------------");
        System.out.print("Employee ID      : ");
        String id = KEYBOARD_SCANNER.nextLine().trim();
        System.out.print("Full Name        : ");
        String name = KEYBOARD_SCANNER.nextLine().trim();
        System.out.print("Department       : ");
        String department = KEYBOARD_SCANNER.nextLine().trim();
        System.out.print("Job Title        : ");
        String jobTitle = KEYBOARD_SCANNER.nextLine().trim();
        System.out.print("Employment Type  : ");
        String typeInput = KEYBOARD_SCANNER.nextLine().trim();
        System.out.print("Date of Joining  : ");
        String doj = KEYBOARD_SCANNER.nextLine().trim();
        System.out.print("Basic Salary     : ");
        double baseSalary = parseSalaryInput(KEYBOARD_SCANNER.nextLine().trim());

        System.out.println();
        System.out.println("[1] Save");
        System.out.println("[2] Cancel");
        System.out.println("----------------------------------------");
        System.out.print("Select option: ");
        String option = KEYBOARD_SCANNER.nextLine().trim();

        if ("2".equals(option)) {
            System.out.println("Add employee cancelled.");
            return;
        }
        if (!"1".equals(option)) {
            throw new IllegalArgumentException("Invalid option");
        }

        Employee employee;
        if ("full-time".equalsIgnoreCase(typeInput) || "fulltime".equalsIgnoreCase(typeInput)
                || "1".equals(typeInput)) {
            employee = new FullTimeEmployee(id, name, department, baseSalary, jobTitle, doj, Employee.Status.ACTIVE);
        } else if ("part-time".equalsIgnoreCase(typeInput) || "parttime".equalsIgnoreCase(typeInput)
                || "2".equals(typeInput)) {
            employee = new PartTimeEmployee(id, name, department, baseSalary, jobTitle, doj, Employee.Status.ACTIVE);
        } else {
            throw new IllegalArgumentException("Employment type must be Full-time or Part-time");
        }

        hrManager.addEmployee(employee);
        hrManager.saveDataToFiles(DATA_DIR);
        System.out.println("Employee added successfully.");
    }

    private static double parseSalaryInput(String salaryInput) {
        String normalized = salaryInput.replace(",", "").trim();
        return Double.parseDouble(normalized);
    }

    private static void updateEmployee(HRManager hrManager) {
        System.out.println("------------ UPDATE EMPLOYEE -----------");
        System.out.print("Enter Employee ID to update: ");
        String id = KEYBOARD_SCANNER.nextLine().trim();

        Employee employee = hrManager.findEmployeeById(id);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found");
        }

        System.out.println();
        System.out.println("Current Information");
        System.out.println("----------------------------------------");
        System.out.println("Name        : " + employee.getName());
        System.out.println("Department  : " + employee.getDepartment());
        System.out.println("Job Title   : " + employee.getJobTitle());
        System.out.printf("Basic Salary: %,.0f%n", employee.getBaseSalary());
        System.out.println("----------------------------------------");
        System.out.println();

        System.out.print("Enter new Department (leave blank to skip): ");
        String newDepartment = KEYBOARD_SCANNER.nextLine().trim();
        System.out.print("Enter new Job Title (leave blank to skip): ");
        String newJobTitle = KEYBOARD_SCANNER.nextLine().trim();

        System.out.println();
        System.out.println("[1] Update");
        System.out.println("[2] Cancel");
        System.out.println("----------------------------------------");
        System.out.print("Select option: ");
        String option = KEYBOARD_SCANNER.nextLine().trim();

        if ("2".equals(option)) {
            System.out.println("Update cancelled.");
            return;
        }
        if (!"1".equals(option)) {
            throw new IllegalArgumentException("Invalid option");
        }

        if (!newDepartment.isEmpty()) {
            employee.setDepartment(newDepartment);
        }
        if (!newJobTitle.isEmpty()) {
            employee.setJobTitle(newJobTitle);
        }

        hrManager.updateEmployeeById(id, employee);
        hrManager.saveDataToFiles(DATA_DIR);
        System.out.println("Employee updated successfully.");
    }

    private static void markTodayAttendance(HRManager hrManager) {
        System.out.print("Employee ID: ");
        String id = KEYBOARD_SCANNER.nextLine().trim();

        System.out.println("Status: 1.PRESENT  2.ABSENT  3.LEAVE");
        System.out.print("Choose status: ");
        String statusChoice = KEYBOARD_SCANNER.nextLine().trim();

        Attendance.AttendanceStatus status;
        switch (statusChoice) {
            case "1":
                status = Attendance.AttendanceStatus.PRESENT;
                break;
            case "2":
                status = Attendance.AttendanceStatus.ABSENT;
                break;
            case "3":
                status = Attendance.AttendanceStatus.LEAVE;
                break;
            default:
                throw new IllegalArgumentException("Invalid status choice");
        }

        hrManager.markEmployeeAttendance(id, status);
        hrManager.saveDataToFiles(DATA_DIR);
        System.out.println("Attendance updated.");
    }

    private static void calculateSalary(HRManager hrManager) {
        System.out.println("\n----------- CALCULATE SALARY -----------");
        System.out.println();
        System.out.print("Employee ID : ");
        String id = KEYBOARD_SCANNER.nextLine().trim();

        Employee employee = hrManager.findEmployeeById(id);
        if (employee == null) {
            System.out.println("Error: Employee not found");
            return;
        }

        System.out.print("Month / Year: ");
        String[] parts = KEYBOARD_SCANNER.nextLine().trim().split("/");
        int month = Integer.parseInt(parts[0].trim());
        int year = Integer.parseInt(parts[1].trim());

        System.out.println();
        System.out.println("[1] Calculate");
        System.out.println("[2] Cancel");
        System.out.println("----------------------------------------");
        System.out.print("Select option: ");
        String option = KEYBOARD_SCANNER.nextLine().trim();

        if ("2".equals(option)) {
            System.out.println("Cancelled.");
            return;
        }
        if (!"1".equals(option)) {
            throw new IllegalArgumentException("Invalid option");
        }

        List<Attendance> records = hrManager.getAttendanceByEmployeeId(id);
        int workingDays = 0;
        int absenceDays = 0;
        double overtimeHours = 0.0;
        for (Attendance a : records) {
            if (a.getDate().getMonthValue() == month && a.getDate().getYear() == year) {
                if (a.getStatus() == Attendance.AttendanceStatus.PRESENT) {
                    workingDays++;
                    overtimeHours += a.getOvertime();
                } else if (a.getStatus() == Attendance.AttendanceStatus.ABSENT) {
                    absenceDays++;
                }
            }
        }

        double totalSalary = hrManager.calculateSalaryById(id, month, year);

        System.out.println();
        System.out.println("Salary calculated successfully.");
        System.out.println();
        System.out.printf("Total Working Days : %d%n", workingDays);
        System.out.printf("Overtime Hours     : %.0f%n", overtimeHours);
        System.out.printf("Absence Days       : %d%n", absenceDays);
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.printf("Total Salary       : %,.0f VND%n", totalSalary);
    }

}