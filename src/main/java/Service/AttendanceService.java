package service;

import entity.*;
import entity.Attendance.AttendanceStatus;
import java.time.LocalDate;
import java.util.*;
import service.*;

public class AttendanceService {

    // ⚠ Phải static luôn
    public static Map<String, List<Attendance>> AttendanceRecord = new HashMap<>();

    // ⚠ Static method
    public static void AttendanceTable(EmployeeService empService) {

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

    public static void markAttendance(String id, AttendanceStatus newStatus) {

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

    public static void showAllAttendance() {

        for (String id : AttendanceRecord.keySet()) {

            System.out.println("ID: " + id);

            for (Attendance record : AttendanceRecord.get(id)) {
                System.out.println(record);
            }

            System.out.println("----------------------");
        }
    }
}