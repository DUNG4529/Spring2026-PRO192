package manager;

import service.ReportService;
import service.EmployeeService;
import service.SalaryService;
import service.AttendanceService;
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
    
    // Adding a new employee
    public void addEmployee(Employee employee) {
        employeeService.addEmployee(employee);
    }

    // ==========================================
    // MODULE 2: ATTENDANCE MANAGEMENT [5, 6]
    // ==========================================
    

    // ==========================================
    // MODULE 3: SALARY MANAGEMENT [5, 6]
    // ==========================================
    
    
    // ==========================================
    // MODULE 4: REPORTING [2, 7]
    // ==========================================
    
    
}