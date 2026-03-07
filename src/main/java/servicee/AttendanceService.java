package servicee;

import entity.*;
import entity.Attendance.AttendanceStatus;
import java.time.LocalDate;
import java.util.*;

public class AttendanceService {

    // Dữ liệu điểm danh được lưu trữ trong một Map, với key là ID nhân viên và value là danh sách các bản ghi điểm danh
    public Map<String, List<Attendance>> AttendanceRecord = new HashMap<>();

    // Constructor
    public AttendanceService() {
    }
    public AttendanceService(Map<String, List<Attendance>> attendanceRecord) {
        this.AttendanceRecord = attendanceRecord;
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
                        new Attendance(today, AttendanceStatus.ABSENT, 0.0);

                AttendanceRecord.get(id).add(defaultRecord);
            }
        }
    }
    // 2. Cập nhật điểm danh cho nhân viên
    public void markAttendance(String id, AttendanceStatus newStatus) {

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
}