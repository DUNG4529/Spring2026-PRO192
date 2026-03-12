package service;

import entity.*;
import entity.Attendance.AttendanceStatus;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AttendanceService {

    // Dữ liệu điểm danh được lưu trữ trong một Map, với key là ID nhân viên và
    // value là danh sách các bản ghi điểm danh
    private Map<String, List<Attendance>> AttendanceRecord = new HashMap<>();
    private EmployeeService employeeService;

    // Constructor
    public AttendanceService() {
    }

    // Constructor có tham số EmployeeService để đảm bảo có thể kiểm tra sự tồn tại
    // của nhân viên khi ghi nhận điểm danh

    public AttendanceService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Constructor có tham số Map để khởi tạo dữ liệu điểm danh từ bên ngoài (ví dụ:
    // khi đọc từ file)
    public AttendanceService(Map<String, List<Attendance>> attendanceRecord) {
        this.AttendanceRecord = attendanceRecord;
    }

    // Constructor đầy đủ tham số để khởi tạo cả dữ liệu điểm danh và
    // EmployeeService
    public AttendanceService(Map<String, List<Attendance>> attendanceRecord, EmployeeService employeeService) {
        this.AttendanceRecord = attendanceRecord;
        this.employeeService = employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Phương thức để lấy bản sao của tất cả dữ liệu điểm danh (để tránh lộ dữ liệu nội bộ)
    public Map<String, List<Attendance>> getAllAttendanceRecords() {
        Map<String, List<Attendance>> snapshot = new HashMap<>();
        // Tạo bản sao sâu (deep copy) của dữ liệu điểm danh để đảm bảo tính bất biến
        for (Map.Entry<String, List<Attendance>> entry : AttendanceRecord.entrySet()) {
            List<Attendance> copiedRecords = new ArrayList<>();
            for (Attendance record : entry.getValue()) {
                copiedRecords.add(new Attendance(
                        record.getIdEmployee(),
                        record.getDate(),
                        record.getStatus(),
                        record.getOvertime()));
            }
            snapshot.put(entry.getKey(), Collections.unmodifiableList(copiedRecords));
        }

        return Collections.unmodifiableMap(snapshot);
    }

    public void clearAll() {
        AttendanceRecord.clear();
    }

    // 1. Khởi tạo bảng điểm danh cho tất cả nhân viên (mặc định là vắng mặt)
    public void AttendanceTable(EmployeeService empService) {

        List<Employee> IDlist = empService.getAllEmp();
        LocalDate today = LocalDate.now();

        for (Employee emp : IDlist) {

            String id = emp.getId();

            AttendanceRecord.putIfAbsent(id, new ArrayList<>());

            if (AttendanceRecord.get(id).isEmpty()) {

                Attendance defaultRecord = new Attendance(id, today, AttendanceStatus.ABSENT, 0.0);

                AttendanceRecord.get(id).add(defaultRecord);
            }
        }
    }

    // 2. Cập nhật điểm danh cho nhân viên
    public void markAttendance(String id, String newStatusStr) {
        validateEmployeeId(id);
        if (!utils.Validation.validAttendanceStatus(newStatusStr)) {
            throw new IllegalArgumentException("Invalid attendance status. Only Present, Absent, or Leave are accepted.");
        }
        AttendanceStatus status = AttendanceStatus.valueOf(newStatusStr.trim().toUpperCase());
        markAttendance(id, status);
    }

    // 2. Cập nhật điểm danh cho nhân viên (Bằng Enum)
    public void markAttendance(String id, AttendanceStatus newStatus) {
        validateEmployeeId(id);
        validateAttendanceStatus(newStatus);

        // BR3: Chỉ chấm công khi nhân viên tồn tại trong hệ thống
        if (!isEmployeeExists(id)) {
            throw new IllegalArgumentException("Employee ID does not exist");
        }

        if (AttendanceRecord.containsKey(id)) {

            List<Attendance> records = AttendanceRecord.get(id);
            LocalDate today = LocalDate.now();

            for (Attendance record : records) {

                if (record.getDate().equals(today)) {
                    record.setStatus(newStatus);
                    return;
                }
            }
        }
    }

    // Ghi nhận điểm danh theo ngày cho 1 nhân viên cụ thể (String)
    public void addAttendance(String id, LocalDate date, String statusStr, double overtime) {
        validateAttendanceInputs(id, date, overtime);
        if (!utils.Validation.validAttendanceStatus(statusStr)) {
            throw new IllegalArgumentException("Invalid attendance status string");
        }
        AttendanceStatus status = AttendanceStatus.valueOf(statusStr.trim().toUpperCase());
        addAttendance(id, date, status, overtime);
    }

    // Ghi nhận điểm danh theo ngày cho 1 nhân viên cụ thể
    public void addAttendance(String id, LocalDate date, AttendanceStatus status, double overtime) {
        validateAttendanceInputs(id, date, overtime);
        validateAttendanceStatus(status);
        validateOvertimeByStatus(status, overtime);

        // BR3: Nhân viên phải tồn tại trước khi ghi nhận điểm danh
        if (!isEmployeeExists(id)) {
            throw new IllegalArgumentException("Employee does not exist");
        }

        AttendanceRecord.putIfAbsent(id, new ArrayList<>());

        // BR4: Mỗi nhân viên chỉ được phép có một bản ghi chấm công cho mỗi ngày
        List<Attendance> records = AttendanceRecord.get(id);
        for (Attendance record : records) {
            if (record.getDate().equals(date)) {
                throw new IllegalArgumentException("Attendance already exists for this date");
            }
        }

        Attendance attendance = new Attendance(id, date, status, overtime);
        records.add(attendance);
    }

    // Cập nhật điểm danh cho một ngày cụ thể
    public void updateAttendance(String id, LocalDate date, AttendanceStatus status, double overtime) {
        validateAttendanceInputs(id, date, overtime);
        validateAttendanceStatus(status);
        validateOvertimeByStatus(status, overtime);

        // BR3: Nhân viên phải tồn tại
        if (!isEmployeeExists(id)) {
            throw new IllegalArgumentException("Employee does not exist");
        }

        List<Attendance> records = AttendanceRecord.get(id);
        if (records == null) {
            throw new IllegalArgumentException("No attendance records found for this employee");
        }

        // Tìm và update record cho ngày cụ thể
        for (Attendance record : records) {
            if (record.getDate().equals(date)) {
                record.setStatus(status);
                record.setOvertime(overtime);
                return;
            }
        }

        throw new IllegalArgumentException("Attendance record not found for this date");
    }

    // 3. Hiển thị bảng điểm danh của tất cả nhân viên
    public String showAllAttendance() {
        StringBuilder output = new StringBuilder();

        List<String> sortedIds = new ArrayList<>(AttendanceRecord.keySet());
        sortedIds.sort((a, b) -> normalizeEmployeeId(a).compareTo(normalizeEmployeeId(b)));

        for (String id : sortedIds) {
            output.append("\nEmployee ID: ").append(id).append("\n");
            output.append("-----------------------------------------\n");
            output.append("Date        Status     Overtime\n");
            output.append("-----------------------------------------\n");

            List<Attendance> records = AttendanceRecord.get(id);
            records.sort((a, b) -> a.getDate().compareTo(b.getDate()));

            for (Attendance record : records) {
                output.append(String.format("%-12s%-11s%s%n",
                        record.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        record.getStatus().getDisplayName(),
                        formatOvertime(record.getOvertime())));
            }
            output.append("-----------------------------------------\n");
        }

        return output.toString();
    }

    private String formatOvertime(double overtime) {
        if (overtime == (long) overtime) {
            return String.format("%d", (long) overtime);
        } else {
            return String.format("%.1f", overtime);
        }
    }

    private String normalizeEmployeeId(String id) {
        if (id == null) {
            return "";
        }
        return id.replace("\uFEFF", "").trim();
    }

    private boolean isEmployeeExists(String id) {
        if (employeeService == null || id == null || id.trim().isEmpty()) {
            return false;
        }
        return employeeService.searchByID(id) != null;
    }

    private void validateAttendanceInputs(String id, LocalDate date, double overtime) {
        validateEmployeeId(id);
        if (date == null) {
            throw new IllegalArgumentException("Attendance date cannot be null");
        }
        if (overtime < 0) {
            throw new IllegalArgumentException("Overtime cannot be negative");
        }
    }

    private void validateEmployeeId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be empty");
        }
    }

    private void validateAttendanceStatus(AttendanceStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Attendance status cannot be null");
        }
    }

    private void validateOvertimeByStatus(AttendanceStatus status, double overtime) {
        if (status != AttendanceStatus.PRESENT && overtime > 0) {
            throw new IllegalArgumentException("Overtime is only allowed when status is PRESENT");
        }
    }
}