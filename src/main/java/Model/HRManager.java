
package Model;

import java.time.LocalDate;
import java.util.List;

/**
 * Lớp HRManager chịu trách nhiệm quản lý:
 * - Danh sách nhân viên (Employee)
 * - Danh sách chấm công (Attendance)
 * - Các nghiệp vụ liên quan đến nhân sự và lương
 */
public class HRManager {

    /**
     * Danh sách nhân viên trong hệ thống
     */
    private List<Employee> employees;

    /**
     * Danh sách bản ghi chấm công
     */
    private List<Attendance> attendances;

    /// =========================
    /// KHỞI TẠO DỮ LIỆU
    /// =========================

    /**
     * Constructor không tham số
     */
    public HRManager() {
    }

    /**
     * Constructor đầy đủ tham số
     *
     * @param employees   Danh sách nhân viên
     * @param attendances Danh sách chấm công
     */
    public HRManager(List<Employee> employees, List<Attendance> attendances) {
        this.employees = employees;
        this.attendances = attendances;
    }

    /// =========================
    /// GETTER & SETTER
    /// =========================

    /**
     * Lấy danh sách nhân viên
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Cập nhật danh sách nhân viên
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Lấy danh sách chấm công
     */
    public List<Attendance> getAttendances() {
        return attendances;
    }

    /**
     * Cập nhật danh sách chấm công
     */
    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    /// =========================
    /// EMPLOYEE METHODS
    /// =========================

    // CRUD fundermentals for Employee

    /**
     * Thêm một nhân viên mới vào hệ thống
     *
     * @param e Nhân viên cần thêm
     */
    public void addEmployee(Employee e) {
        // TODO: implement later
    }

    /**
     * Cập nhật thông tin nhân viên dựa trên ID
     *
     * @param idEmployee  Mã nhân viên cần cập nhật
     * @param newEmployee Thông tin nhân viên mới
     */
    public void updateEmployeeById(String idEmployee, Employee newEmployee) {
        // TODO: implement later
    }

    /**
     * Xóa nhân viên khỏi hệ thống theo ID
     *
     * @param idEmployee Mã nhân viên cần xóa
     */
    public void deleteEmployeeById(String idEmployee) {
        // TODO: implement later
    }

    /**
     * Tìm nhân viên theo ID
     *
     * @param idEmployee Mã nhân viên cần tìm
     * @return Employee nếu tìm thấy, null nếu không tồn tại
     */
    public Employee findEmployeeById(String idEmployee) {
        // TODO: implement later
        return null;
    }

    /// =========================
    /// ATTENDANCE METHODS
    /// =========================

    /**
     * Thêm một bản ghi chấm công mới
     *
     * @param a Bản ghi chấm công
     */
    public void addAttendance(Attendance a) {
        // TODO: implement later
    }

    /**
     * Cập nhật thông tin chấm công của nhân viên
     *
     * @param idEmployee Mã nhân viên
     * @param date       Ngày chấm công
     * @param status     Trạng thái (Present/Absent/Late/...)
     * @param overtime   Số giờ làm thêm
     */
    public void updateAttendance(String idEmployee, LocalDate date,
                                 String status, double overtime) {
        // TODO: implement later
    }

    /**
     * Xóa bản ghi chấm công của nhân viên theo ngày
     *
     * @param idEmployee Mã nhân viên
     * @param date       Ngày cần xóa
     */
    public void deleteAttendance(String idEmployee, LocalDate date) {
        // TODO: implement later
    }

    /// =========================
    /// SALARY METHOD
    /// =========================

    /**
     * Tính lương của nhân viên dựa trên:
     * - Ngày công
     * - Trạng thái làm việc
     * - Giờ tăng ca
     *
     * @param idEmployee Mã nhân viên
     * @return Tổng lương của nhân viên
     */
    public double calculateSalaryById(String idEmployee) {
        // TODO: implement later
        return 0.0;
    }
}
