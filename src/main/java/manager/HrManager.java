package manager;

import entity.*;
import service.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HRManager {
    // 1. Khai báo các List chứa dữ liệu gốc theo đúng yêu cầu thiết kế [2, 3]
    private List<Employee> employees;
    private List<Attendance> attendances;

    // 2. Khai báo các Service xử lý logic (Decomposition) [4]
    private EmployeeService employeeService;
    private AttendanceService attendanceService;
    private SalaryService salaryService;
    private ReportService reportService;

    // Constructor: Khởi tạo danh sách và "bơm" (inject) vào các Service
    public HRManager() {
        this.employees = new ArrayList<>();
        this.attendances = new ArrayList<>();

        // Khởi tạo các Service, truyền List vào để chúng thao tác trực tiếp trên dữ liệu gốc
        this.employeeService = new EmployeeService(this.employees); // Truyền danh sách nhân viên để EmployeeService có thể quản lý
        this.attendanceService = new AttendanceService(this.attendances, this.employees); // Truyền cả danh sách nhân viên để AttendanceService có thể cập nhật trạng thái attendance
        this.salaryService = new SalaryService(this.employees, this.attendances); // Truyền cả hai danh sách để SalaryService có thể tính lương dựa trên attendance
        this.reportService = new ReportService(this.employees, this.attendances); // Truyền cả hai danh sách để ReportService có thể tạo báo cáo dựa trên dữ liệu nhân viên và attendance
    }

    // ==========================================
    // MODULE 1: EMPLOYEE MANAGEMENT [5, 6]
    // ==========================================
    
    public void addEmployee(Employee e) {
        employeeService.addEmployee(e);
    }

    public Employee findEmployeeById(String idEmployee) {
        int index = employeeService.searchID(idEmployee);
        if (index == -1) {
            return null;
        }
        return employeeService.getEmpIndex(index);
    }

    public void updateEmployeeById(String idEmployee, Employee newEmployee) {
        newEmployee.setId(idEmployee);
        employeeService.updateEmployee(newEmployee);
    }

    public void deleteEmployeeById(String idEmployee) {
        employeeService.deleteEmployee(idEmployee);
    }

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmp();
    }

    // ==========================================
    // MODULE 2: ATTENDANCE MANAGEMENT [5, 6]
    // ==========================================
    
    public void addAttendance(Attendance a) {
        attendanceService.addAttendance(a);
    }

    public void updateAttendance(String idEmployee, LocalDate date, String status, double overtime) {
        // Ủy quyền cho AttendanceService xử lý việc tìm và cập nhật
        attendanceService.updateAttendance(idEmployee, date, status, overtime);
    }

    public void deleteAttendance(String idEmployee, LocalDate date) {
        attendanceService.deleteAttendance(idEmployee, date);
    }

    // ==========================================
    // MODULE 3: SALARY MANAGEMENT [5, 6]
    // ==========================================
    
    public double calculateSalaryById(String idEmployee) {
        Employee employee = findEmployeeById(idEmployee);
        if (employee == null) {
            return 0.0;
        }
        return salaryService.calculateEmployeeSalary(
                employee,
                attendances,
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear());
    }

    public double calculateSalaryById(String idEmployee, int month, int year) {
        Employee employee = findEmployeeById(idEmployee);
        if (employee == null) {
            return 0.0;
        }
        return salaryService.calculateEmployeeSalary(employee, attendances, month, year);
    }

    public List<Attendance> getAttendanceByEmployeeId(String idEmployee) {
        List<Attendance> result = new ArrayList<>();
        if (idEmployee == null || idEmployee.trim().isEmpty()) {
            return result;
        }
        for (Attendance attendance : attendances) {
            if (attendance != null
                    && attendance.getIdEmployee() != null
                    && attendance.getIdEmployee().equalsIgnoreCase(idEmployee)) {
                result.add(attendance);
            }
        }
        return result;
    }

    // ==========================================
    // MODULE 4: REPORTING [2, 7]
    // ==========================================
    
    // BR12: Báo cáo nhân viên đi làm ít (Vắng mặt nhiều) [8]
    public void reportLowAttendance(int thresholdDays) {
        reportService.generateLowAttendanceReport(
            employees,
            attendances,
            LocalDate.now().getMonthValue(),
            LocalDate.now().getYear(),
            thresholdDays);
    }

    // BR13: Báo cáo nhân viên lương cao nhất [9]
    public void reportHighestPaid() {
        reportService.generateHighestPaidEmployeesReport(
                employees,
                attendances,
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear());
    }
}