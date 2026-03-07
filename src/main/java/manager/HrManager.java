package manager;

import servicee.ReportService;
import servicee.EmployeeService;
import servicee.SalaryService;
import servicee.AttendanceService;
import entity.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HRManager {
    // 1. Khai báo các List chứa dữ liệu gốc theo đúng yêu cầu thiết kế [2, 3]
    private List<Employee> employees;
    private List<Attendance> attendances;

    // 2. Khai báo các Service xử lý logic (Decomposition) [4]
    private EmployeeService employeeService = new EmployeeService(employees);
    private AttendanceService attendanceService = new AttendanceService();
    private SalaryService salaryService = new SalaryService(employees, attendances);
    private ReportService reportService = new ReportService(employees, attendances);

    // Constructor: Khởi tạo danh sách và "bơm" (inject) vào các Service
    public HRManager() {
        this.employees = new ArrayList<>();
        this.attendances = new ArrayList<>();

        // Inject dữ liệu vào các Service
        this.employeeService = new EmployeeService(employees);
        this.attendanceService = new AttendanceService();

        // Lưu ý: SalaryService và ReportService cần cả employees và attendances, nên phải khởi tạo sau khi đã tạo xong 2 List này
        this.salaryService = new SalaryService(employees, attendances);
        this.reportService = new ReportService(employees, attendances);
    }

    // ==========================================
    // MODULE 1: EMPLOYEE MANAGEMENT [5, 6]
    // ==========================================
    // Các phương thức quản lý nhân viên (thêm, sửa, xóa, tìm kiếm)
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
    public void initializeAttendance() {
        attendanceService.AttendanceTable(employeeService);
    }

    public void markEmployeeAttendance(String idEmployee, Attendance.AttendanceStatus status) {
        attendanceService.markAttendance(idEmployee, status);
    }
    public void showAllAttendance() {
        attendanceService.showAllAttendance();
    }

    // ==========================================
    // MODULE 3: SALARY MANAGEMENT [5, 6]
    // ==========================================
    
    // Tính lương cho một nhân viên theo ID, tháng và năm hiện tại
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
    // Tính lương cho một nhân viên theo ID, tháng và năm cụ thể (cho phép nhập tháng/năm khác)
    public double calculateSalaryById(String idEmployee, int month, int year) {
        Employee employee = findEmployeeById(idEmployee);
        if (employee == null) {
            return 0.0;
        }
        return salaryService.calculateEmployeeSalary(employee, attendances, month, year);
    }
    // Hiển thị bảng lương cho tất cả nhân viên trong tháng hiện tại
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