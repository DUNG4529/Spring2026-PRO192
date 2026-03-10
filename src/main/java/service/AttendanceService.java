package service;

import entity.*;
import entity.Attendance.AttendanceStatus;
import java.time.LocalDate;
import java.util.*;

public class AttendanceService {

    // Dữ liệu điểm danh được lưu trữ trong một Map, với key là ID nhân viên và value là danh sách các bản ghi điểm danh
    public Map<String, List<Attendance>> AttendanceRecord = new HashMap<>();
    private EmployeeService employeeService;

    // Constructor
    public AttendanceService() {
    }

    // Constructor có tham số EmployeeService để đảm bảo có thể kiểm tra sự tồn tại của nhân viên khi ghi nhận điểm danh

    public AttendanceService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Constructor có tham số Map để khởi tạo dữ liệu điểm danh từ bên ngoài (ví dụ: khi đọc từ file)
    public AttendanceService(Map<String, List<Attendance>> attendanceRecord) {
        this.AttendanceRecord = attendanceRecord;
    }
    // Constructor đầy đủ tham số để khởi tạo cả dữ liệu điểm danh và EmployeeService
    public AttendanceService(Map<String, List<Attendance>> attendanceRecord, EmployeeService employeeService) {
        this.AttendanceRecord = attendanceRecord;
        this.employeeService = employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 1. Khởi tạo bảng điểm danh cho tất cả nhân viên (mặc định là vắng mặt)
    public void AttendanceTable(EmployeeService empService) {

        List<Employee> IDlist = empService.getAllEmp();
        LocalDate today = LocalDate.now();

        for (Employee emp : IDlist) {

            String id = emp.getId();

            AttendanceRecord.putIfAbsent(id, new ArrayList<>());

            if (AttendanceRecord.get(id).isEmpty()) {

                Attendance defaultRecord =
                    new Attendance(id, today, AttendanceStatus.ABSENT, 0.0);

                AttendanceRecord.get(id).add(defaultRecord);
            }
        }
    }
    // 2. Cập nhật điểm danh cho nhân viên
    public void markAttendance(String id, AttendanceStatus newStatus) {

        // BR3: Chỉ chấm công khi nhân viên tồn tại trong hệ thống
        if (!isEmployeeExists(id)) {
            System.out.println("Không tìm thấy mã nhân viên!");
            return;
        }

        if (AttendanceRecord.containsKey(id)) {

            List<Attendance> records = AttendanceRecord.get(id);
            LocalDate today = LocalDate.now();

            for (Attendance record : records) {

                if (record.getDate().equals(today)) {
                    record.setStatus(newStatus);
                    System.out.println("Cập nhật thành công cho ID: " + id);
                    return;
                }
            }

        } else {
            System.out.println("Không tìm thấy mã nhân viên!");
        }
    }

    // Ghi nhận điểm danh theo ngày cho 1 nhân viên cụ thể
    public void addAttendance(String id, LocalDate date, AttendanceStatus status, double overtime) {
        // BR3: Nhân viên phải tồn tại trước khi ghi nhận điểm danh
        if (!isEmployeeExists(id)) {
            throw new IllegalArgumentException("Employee does not exist");
        }

        AttendanceRecord.putIfAbsent(id, new ArrayList<>());

        Attendance attendance = new Attendance(id, date, status, overtime);
        AttendanceRecord.get(id).add(attendance);
    }

    // 3. Hiển thị bảng điểm danh của tất cả nhân viên
    public void showAllAttendance() {

        for (String id : AttendanceRecord.keySet()) {

            System.out.println("ID: " + id);

            for (Attendance record : AttendanceRecord.get(id)) {
                System.out.println(record);
            }

            System.out.println("----------------------");
        }
    }

    private boolean isEmployeeExists(String id) {
        if (employeeService == null || id == null || id.trim().isEmpty()) {
            return false;
        }
        return employeeService.searchByID(id) != null;
    }
}