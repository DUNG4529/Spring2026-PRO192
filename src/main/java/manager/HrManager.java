package manager;

import service.*;
import entity.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;

public class HRManager {
    private static final String EMPLOYEE_FILE_NAME = "employees.txt";
    private static final String ATTENDANCE_FILE_NAME = "attendance.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Các service được HRManager sử dụng để quản lý nhân viên, điểm danh, lương và báo cáo
    private final EmployeeService employeeService;
    private final AttendanceService attendanceService;
    private final SalaryService salaryService;
    private final ReportService reportService;

    // Constructor khởi tạo tất cả các service
    public HRManager() {
        this.employeeService = new EmployeeService();
        this.attendanceService = new AttendanceService(employeeService);
        this.salaryService = new SalaryService();
        this.reportService = new ReportService();
    }

    // =========================
    // Manager Methods for Employee Management
    // =========================

    public void addEmployee(Employee employee) {
        employeeService.addEmployee(employee);
    }

    public Employee findEmployeeById(String idEmployee) {
        validateEmployeeId(idEmployee);
        return employeeService.getEmployee(idEmployee);
    }

    public void updateEmployeeById(String idEmployee, Employee newEmployee) {
        validateEmployeeId(idEmployee);
        if (newEmployee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (!newEmployee.getId().equals(idEmployee)) {
            throw new IllegalArgumentException("Employee ID cannot be changed");
        }
        employeeService.updateEmployee(newEmployee);
    }

    public void deleteEmployeeById(String idEmployee) {
        validateEmployeeId(idEmployee);
        employeeService.deleteEmployee(idEmployee);
    }

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmp();
    }

    // =========================
    // Manager Methods for Attendance Management
    // =========================

    public void initializeAttendance() {
        attendanceService.AttendanceTable(employeeService);
    }

    public void markEmployeeAttendance(String idEmployee, Attendance.AttendanceStatus status) {
        validateEmployeeId(idEmployee);
        if (status == null) {
            throw new IllegalArgumentException("Attendance status cannot be null");
        }
        Employee employee = findEmployeeById(idEmployee);
        if (employee == null) {
            throw new IllegalArgumentException("Employee does not exist");
        }
        attendanceService.markAttendance(idEmployee, status);
    }

    public void addAttendance(Attendance attendance) {
        if (attendance == null) {
            throw new IllegalArgumentException("Attendance cannot be null");
        }
        Employee employee = findEmployeeById(attendance.getIdEmployee());
        if (employee == null) {
            throw new IllegalArgumentException("Employee does not exist");
        }
        attendanceService.addAttendance(
                attendance.getIdEmployee(),
                attendance.getDate(),
                attendance.getStatus(),
                attendance.getOvertime());
    }

    public List<Attendance> getAttendanceByEmployeeId(String idEmployee) {
        validateEmployeeId(idEmployee);
        List<Attendance> records = attendanceService.getAllAttendanceRecords().get(idEmployee);
        if (records == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(records);
    }

    public String showAllAttendance() {
        return attendanceService.showAllAttendance();
    }

    // =========================
    // Manager Methods for Salary Calculation and Reporting
    // =========================

    public double calculateSalaryById(String idEmployee) {
        return calculateSalaryById(idEmployee, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }

    public double calculateSalaryById(String idEmployee, int month, int year) {
        validateEmployeeId(idEmployee);
        validateMonthYear(month, year);
        Employee employee = findEmployeeById(idEmployee);
        if (employee == null) {
            return 0.0;
        }
        return salaryService.calculateEmployeeSalary(employee, getAllAttendances(), month, year);
    }

    // ==========================
    // Manager Methods for Reporting
    // ==========================

    public String reportLowAttendance(int thresholdDays) {
        if (thresholdDays < 0) {
            throw new IllegalArgumentException("Threshold days cannot be negative");
        }
        return reportService.generateLowAttendanceReport(
                getAllEmployees(),
                getAllAttendances(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear(),
                thresholdDays);
    }

    public String reportHighestPaid() {
        return reportHighestPaid(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }

    public String reportHighestPaid(int month, int year) {
        return reportService.generateHighestPaidEmployeesReport(
                getAllEmployees(),
                getAllAttendances(),
                month,
                year);
    }

    public void loadDataFromFiles(String dataDirectoryPath) {
        if (dataDirectoryPath == null || dataDirectoryPath.trim().isEmpty()) {
            throw new IllegalArgumentException("Data directory path cannot be empty");
        }

        File dataDirectory = new File(dataDirectoryPath);
        if (!dataDirectory.exists()) {
            if (!dataDirectory.mkdirs()) {
                throw new IllegalStateException("Cannot create data directory: " + dataDirectoryPath);
            }
        }

        // Ensure AttendanceService always uses the current EmployeeService instance
        // before loading persisted data.
        attendanceService.setEmployeeService(employeeService);

        employeeService.clearAll();
        attendanceService.clearAll();

        loadEmployees(new File(dataDirectory, EMPLOYEE_FILE_NAME));
        loadAttendance(new File(dataDirectory, ATTENDANCE_FILE_NAME));
    }

    public void saveDataToFiles(String dataDirectoryPath) {
        if (dataDirectoryPath == null || dataDirectoryPath.trim().isEmpty()) {
            throw new IllegalArgumentException("Data directory path cannot be empty");
        }

        File dataDirectory = new File(dataDirectoryPath);
        if (!dataDirectory.exists() && !dataDirectory.mkdirs()) {
            throw new IllegalStateException("Cannot create data directory: " + dataDirectoryPath);
        }

        saveEmployees(new File(dataDirectory, EMPLOYEE_FILE_NAME));
        saveAttendance(new File(dataDirectory, ATTENDANCE_FILE_NAME));
    }

    private List<Attendance> getAllAttendances() {
        List<Attendance> result = new ArrayList<>();
        for (Map.Entry<String, List<Attendance>> entry : attendanceService.getAllAttendanceRecords().entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }

    private void loadEmployees(File employeeFile) {
        if (!employeeFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(employeeFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);
                if (parts.length != 8) {
                    continue;
                }

                String id = sanitizeField(parts[0]);
                String name = sanitizeField(parts[1]);
                String department = sanitizeField(parts[2]);
                String jobTitle = sanitizeField(parts[3]);
                String dateOfJoining = sanitizeField(parts[4]);
                // Sanitize salary: remove commas if present (e.g., "12,000,000" -> "12000000")
                String salaryStr = sanitizeField(parts[5]).replace(",", "");
                double baseSalary = Double.parseDouble(salaryStr);
                Employee.Status status = Employee.Status.valueOf(sanitizeField(parts[6]));
                String type = sanitizeField(parts[7]);

                Employee employee;
                if ("FULL_TIME".equalsIgnoreCase(type)) {
                    employee = new FullTimeEmployee(id, name, department, baseSalary, jobTitle, dateOfJoining, status);
                } else if ("PART_TIME".equalsIgnoreCase(type)) {
                    employee = new PartTimeEmployee(id, name, department, baseSalary, jobTitle, dateOfJoining, status);
                } else {
                    continue;
                }

                employeeService.addEmployee(employee);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read employees file: " + employeeFile.getPath(), e);
        }
    }

    private void loadAttendance(File attendanceFile) {
        if (!attendanceFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(attendanceFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);
                if (parts.length != 4) {
                    continue;
                }

                String employeeId = sanitizeField(parts[0]);
                LocalDate date = LocalDate.parse(sanitizeField(parts[1]), DATE_FORMATTER);
                Attendance.AttendanceStatus status = Attendance.AttendanceStatus.valueOf(sanitizeField(parts[2]));
                double overtime = Double.parseDouble(sanitizeField(parts[3]));

                attendanceService.addAttendance(employeeId, date, status, overtime);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read attendance file: " + attendanceFile.getPath(), e);
        }
    }

    private void saveEmployees(File employeeFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(employeeFile, false))) {
            for (Employee employee : getAllEmployees()) {
                String type = employee instanceof FullTimeEmployee ? "FULL_TIME" : "PART_TIME";
                writer.write(String.join("|",
                        employee.getId(),
                        employee.getName(),
                        employee.getDepartment(),
                        employee.getJobTitle(),
                        employee.getDateOfJoining(),
                        String.valueOf(employee.getBaseSalary()),
                        employee.getStatus().name(),
                        type));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write employees file: " + employeeFile.getPath(), e);
        }
    }

    private void saveAttendance(File attendanceFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(attendanceFile, false))) {
            Map<String, List<Attendance>> attendanceMap = attendanceService.getAllAttendanceRecords();
            for (Map.Entry<String, List<Attendance>> entry : attendanceMap.entrySet()) {
                for (Attendance attendance : entry.getValue()) {
                    writer.write(String.join("|",
                            attendance.getIdEmployee(),
                            attendance.getDate().format(DATE_FORMATTER),
                            attendance.getStatus().name(),
                            String.valueOf(attendance.getOvertime())));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write attendance file: " + attendanceFile.getPath(), e);
        }
    }

    private String sanitizeField(String raw) {
        if (raw == null) {
            return "";
        }
        return raw.replace("\uFEFF", "").trim();
    }

    // ========================
    // Helper Methods for Validation
    // ========================
    private void validateEmployeeId(String idEmployee) {
        if (idEmployee == null || idEmployee.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be empty");
        }
    }

    private void validateMonthYear(int month, int year) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be greater than 0");
        }
    }
}