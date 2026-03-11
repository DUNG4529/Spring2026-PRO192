package service;
    
import entity.Attendance;
import entity.Employee;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SalaryService {

    private static final class AttendanceSummary {
        private int workingDays;
        private int absenceDays;
        private int leaveDays;
        private double overtimeHours;
    }

    List<Employee> employeeList;
    List<Attendance> attendanceList;
    // Constructor
    public SalaryService() {
    }
    public SalaryService(List<Employee> employeeList, List<Attendance> attendanceList) {
        this.employeeList = employeeList;
        this.attendanceList = attendanceList;
    }

    // 1. Tính lương nhân viên (dựa trên: Lương cơ bản, Ngày công, Giờ làm thêm, Số
    // ngày nghỉ)
    public double calculateEmployeeSalary(Employee employee, List<Attendance> attendances, int month, int year) {
        // BR10: Salary can only be calculated for active employees.
        if (employee.getStatus() != Employee.Status.ACTIVE) {
            return 0.0;
        }

        AttendanceSummary summary = summarizeAttendance(employee, attendances, month, year);
        return employee.calculateSalary(summary.workingDays, summary.absenceDays, summary.overtimeHours);
    }

    // 2. Xem bảng lương tất cả nhân viên
    public void displayAllSalaries(List<Employee> employees, List<Attendance> attendances, int month, int year) {
        System.out.println(
                "====================================================================================================");
        System.out.printf(" BẢNG LƯƠNG NHÂN VIÊN THÁNG %d/%d\n", month, year);
        System.out.println(
                "====================================================================================================");
        System.out.printf("%-10s | %-20s | %-15s | %-12s | %-12s | %-12s | %-15s\n",
            "ID", "Name", "Role", "Working Days", "Absence Days", "Leave Days", "Total Salary");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");

        for (Employee emp : employees) {
            if (emp.getStatus() != Employee.Status.ACTIVE)
                continue;

            double salary = calculateEmployeeSalary(emp, attendances, month, year);
            AttendanceSummary summary = summarizeAttendance(emp, attendances, month, year);

            System.out.printf("%-10s | %-20s | %-15s | %-12d | %-12d | %-12d | %-15.2f\n",
                    emp.getId(), emp.getName(), emp.getClass().getSimpleName(), summary.workingDays,
                    summary.absenceDays, summary.leaveDays, salary);
        }
        System.out.println(
                "====================================================================================================");
    }

    // Task B6 — Calculate Salary (Detail for a single employee)
    public void displaySalaryDetail(Employee employee, List<Attendance> attendances, int month, int year) {
        System.out.println("----------- CALCULATE SALARY -----------");
        // BR10: Salary can only be calculated for active employees.
        // Valid employee status check

        System.out.printf("Employee ID: %s\n", employee.getId());
        System.out.printf("Month / Year: %d / %d\n", month, year);

        try {
            if (employee.getStatus() != Employee.Status.ACTIVE) {
                throw new IllegalStateException("Salary can only be calculated for active employees.");
            }
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        AttendanceSummary summary = summarizeAttendance(employee, attendances, month, year);
        double totalSalary = employee.calculateSalary(summary.workingDays, summary.absenceDays, summary.overtimeHours);

        System.out.println("Output");
        System.out.println("Salary calculated successfully.");
        System.out.printf("Total Working Days: %d\n", summary.workingDays);
        System.out.printf("Overtime Hours: %.2f\n", summary.overtimeHours);
        System.out.printf("Absence Days: %d\n", summary.absenceDays);
        System.out.printf("Leave Days: %d\n", summary.leaveDays);
        System.out.printf("Total Salary: %,.0f VND\n", totalSalary);
    }

    // 3. Xuất bảng lương ra file
    public void exportSalariesToFile(List<Employee> employees, List<Attendance> attendances, int month, int year,
            String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(String.format("BẢNG LƯƠNG NHÂN VIÊN THÁNG %d/%d\n", month, year));
            writer.write("ID,Name,Role,Working Days,Absence Days,Leave Days,Total Salary\n");

            for (Employee emp : employees) {
                if (emp.getStatus() != Employee.Status.ACTIVE)
                    continue;

                double salary = calculateEmployeeSalary(emp, attendances, month, year);
                AttendanceSummary summary = summarizeAttendance(emp, attendances, month, year);

                writer.write(String.format("%s,%s,%s,%d,%d,%d,%.2f\n",
                        emp.getId(), emp.getName(), emp.getClass().getSimpleName(), summary.workingDays,
                        summary.absenceDays, summary.leaveDays, salary));
            }
            System.out.println("=> Xuất bảng lương ra file thành công: " + filePath);
        } catch (IOException e) {
            System.out.println("=> Lỗi khi xuất file: " + e.getMessage());
        }
    }

    private AttendanceSummary summarizeAttendance(Employee employee, List<Attendance> attendances, int month, int year) {
        AttendanceSummary summary = new AttendanceSummary();

        for (Attendance attendance : attendances) {
            if (!attendance.getIdEmployee().equals(employee.getId())) {
                continue;
            }
            if (attendance.getDate().getMonthValue() != month || attendance.getDate().getYear() != year) {
                continue;
            }

            if (attendance.getStatus() == Attendance.AttendanceStatus.PRESENT) {
                summary.workingDays++;
            } else if (attendance.getStatus() == Attendance.AttendanceStatus.ABSENT) {
                summary.absenceDays++;
            } else if (attendance.getStatus() == Attendance.AttendanceStatus.LEAVE) {
                summary.leaveDays++;
            }

            summary.overtimeHours += attendance.getOvertime();
        }

        return summary;
    }
}
