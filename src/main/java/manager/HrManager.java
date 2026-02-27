
package manager;

import java.util.ArrayList;
import java.util.List;
import entity.Attendance;
import entity.Employee;
import entity.FullTimeEmployee;

public class HrManager {
    // Sử dụng ArrayList để quản lý dữ liệu động
    private List<Employee> employees;
    private List<Attendance> attendances;

    public HrManager() {
        this.employees = new ArrayList<>();
        this.attendances = new ArrayList<>();
    }

    // --- QUẢN LÝ NHÂN VIÊN (CRUD) ---

    // Thêm nhân viên mới (BR1: Kiểm tra ID duy nhất) 
    public void addEmployee(Employee e) {
        // Logic: Kiểm tra ID đã tồn tại chưa, nếu chưa thì thêm vào danh sách 
        // ... 
    }

    // Tìm nhân viên theo ID 
    public Employee findEmployeeById(String id) {
        // Logic: Duyệt qua danh sách nhân viên để tìm kiếm theo ID 
        // ...
        return null; // Trả về null nếu không tìm thấy
    }

    // Xóa nhân viên 
    public void deleteEmployeeById(String id) {
        // Logic: Tìm nhân viên theo ID và xóa khỏi danh sách (hoặc đánh dấu INACTIVE)
        // ...
    }

    // --- QUẢN LÝ CHẤM CÔNG ---

    // Ghi nhận chấm công (BR3: Nhân viên phải tồn tại) 
    public void addAttendance(Attendance a) {
        // Logic: Kiểm tra nhân viên tồn tại, sau đó thêm thông tin chấm công vào danh sách 
        // ...
    }

    // --- TÍNH LƯƠNG & BÁO CÁO ---

    // Tính lương dựa trên Business Rules (BR7, BR8, BR9) 
    public double calculateSalaryById(String idEmployee, int month, int year) {
        // Logic: Tìm nhân viên theo ID, lấy thông tin chấm công trong tháng đó, tính lương dựa trên loại nhân viên và các quy tắc đã định 
        // ...
        return 0.0; // Trả về lương đã tính
    }

    // Báo cáo nhân viên lương cao nhất (B8)
    public void showHighestPaidEmployees() {
        // Logic: Tìm giá trị lương lớn nhất và in ra danh sách nhân viên có lương cao nhất
        // ...
        System.out.println("----------- HIGHEST PAID EMPLOYEES -----------");
    }
}