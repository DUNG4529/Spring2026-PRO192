package manager;

import service.*;
import entity.*;
import java.util.*;
import java.time.LocalDate;

public class HRManager {
    // Các service được HRManager sử dụng để quản lý nhân viên, điểm danh, lương và
    // báo cáo
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
        return employeeService.getEmployee(idEmployee);
    }

    public void updateEmployeeById(String idEmployee, Employee newEmployee) {
        if (newEmployee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (!newEmployee.getId().equals(idEmployee)) {
            throw new IllegalArgumentException("Employee ID cannot be changed");
        }
        employeeService.updateEmployee(newEmployee);
    }

    public void deleteEmployeeById(String idEmployee) {
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
        List<Attendance> records = attendanceService.getAllAttendanceRecords().get(idEmployee);
        if (records == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(records);
    }

    public void showAllAttendance() {
        attendanceService.showAllAttendance();
    }

    // =========================
    // Manager Methods for Salary Calculation and Reporting
    // =========================

    public double calculateSalaryById(String idEmployee) {
        return calculateSalaryById(idEmployee, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    }

    public double calculateSalaryById(String idEmployee, int month, int year) {
        Employee employee = findEmployeeById(idEmployee);
        if (employee == null) {
            return 0.0;
        }
        return salaryService.calculateEmployeeSalary(employee, getAllAttendances(), month, year);
    }

    // ==========================
    // Manager Methods for Reporting
    // ==========================

    public void reportLowAttendance(int thresholdDays) {
        reportService.generateLowAttendanceReport(
                getAllEmployees(),
                getAllAttendances(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear(),
                thresholdDays);
    }

    public void reportHighestPaid() {
        reportService.generateHighestPaidEmployeesReport(
                getAllEmployees(),
                getAllAttendances(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear());
    }

    private List<Attendance> getAllAttendances() {
        List<Attendance> result = new ArrayList<>();
        for (Map.Entry<String, List<Attendance>> entry : attendanceService.getAllAttendanceRecords().entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }
}