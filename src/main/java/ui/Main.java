package ui;

import manager.HRManager;
import entity.*;
import service.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo HRManager
        HRManager hrManager = new HRManager();

        // Thêm một số nhân viên
        hrManager.addEmployee(new Employee("E001", "Alice", "HR", 5000, Employee.Status.ACTIVE));
        hrManager.addEmployee(new Employee("E002", "Bob", "IT", 6000, Employee.Status.ACTIVE));
        hrManager.addEmployee(new Employee("E003", "Charlie", "Finance", 5500, Employee.Status.ACTIVE));

        // Khởi tạo bảng điểm danh cho tất cả nhân viên
        hrManager.attendanceService.AttendanceTable(hrManager.employeeService);

        // Cập nhật điểm danh cho một nhân viên
        hrManager.attendanceService.markAttendance("E001", Attendance.AttendanceStatus.PRESENT);
        hrManager.attendanceService.markAttendance("E002", Attendance.AttendanceStatus.ABSENT);
        hrManager.attendanceService.markAttendance("E003", Attendance.AttendanceStatus.LEAVE);

        // In ra bảng điểm danh để kiểm tra
        for (String empId : hrManager.attendanceService.AttendanceRecord.keySet()) {
            System.out.println("Employee ID: " + empId);
            for (Attendance attendance : hrManager.attendanceService.AttendanceRecord.get(empId)) {
                System.out.println("  Date: " + attendance.getDate() + ", Status: " + attendance.getStatus().getDisplayName() + ", Overtime: " + attendance.getOvertime());
            }
        }
    }
}