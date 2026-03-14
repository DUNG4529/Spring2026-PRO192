package service;
    
import entity.Attendance;
import entity.Employee;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class SalaryService {

    public static final class AttendanceSummary {
        private final int workingDays;
        private final int absenceDays;
        private final int leaveDays;
        private final double overtimeHours;

        public AttendanceSummary(int workingDays, int absenceDays, int leaveDays, double overtimeHours) {
            this.workingDays = workingDays;
            this.absenceDays = absenceDays;
            this.leaveDays = leaveDays;
            this.overtimeHours = overtimeHours;
        }

        public int getWorkingDays() {
            return workingDays;
        }

        public int getAbsenceDays() {
            return absenceDays;
        }

        public int getLeaveDays() {
            return leaveDays;
        }

        public double getOvertimeHours() {
            return overtimeHours;
        }
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

    // 1. Tính lương nhân viên (dựa trên: Lương cơ bản, Ngày công, Giờ làm thêm, Số ngày nghỉ)
    public double calculateEmployeeSalary(Employee employee, List<Attendance> attendances, int month, int year) {
        validateEmployeeAttendanceInputs(employee, attendances, month, year);

        // BR10: Salary can only be calculated for active employees.
        if (employee.getStatus() != Employee.Status.ACTIVE) {
            return 0.0;
        }

        AttendanceSummary summary = getAttendanceSummary(employee, attendances, month, year);
        return employee.calculateSalary(summary.getWorkingDays(), summary.getAbsenceDays(), summary.getOvertimeHours());
    }

    public AttendanceSummary getAttendanceSummary(Employee employee, List<Attendance> attendances, int month, int year) {
        validateEmployeeAttendanceInputs(employee, attendances, month, year);
        return summarizeAttendance(employee, attendances, month, year);
    }

    // 2. Xem bảng lương tất cả nhân viên
        public String displayAllSalaries(List<Employee> employees, List<Attendance> attendances, int month, int year) {
        validateEmployeesAttendanceInputs(employees, attendances, month, year);

        StringBuilder output = new StringBuilder();

        output.append("====================================================================================================\n");
        output.append(String.format(" BẢNG LƯƠNG NHÂN VIÊN THÁNG %d/%d\n", month, year));
        output.append("====================================================================================================\n");
        output.append(String.format("%-10s | %-20s | %-15s | %-12s | %-12s | %-12s | %-15s\n",
            "ID", "Name", "Role", "Working Days", "Absence Days", "Leave Days", "Total Salary"));
        output.append("----------------------------------------------------------------------------------------------------\n");

        for (Employee emp : employees) {
            if (emp.getStatus() != Employee.Status.ACTIVE)
                continue;

            double salary = calculateEmployeeSalary(emp, attendances, month, year);
                AttendanceSummary summary = getAttendanceSummary(emp, attendances, month, year);

                output.append(String.format("%-10s | %-20s | %-15s | %-12d | %-12d | %-12d | %-15s\n",
                    emp.getId(), emp.getName(), emp.getClass().getSimpleName(), summary.getWorkingDays(),
                    summary.getAbsenceDays(), summary.getLeaveDays(), formatVndAmount(salary)));
        }
        output.append("====================================================================================================");
        return output.toString();
    }

    // Task B6 — Calculate Salary (Detail for a single employee)
    public String displaySalaryDetail(Employee employee, List<Attendance> attendances, int month, int year) {
        validateEmployeeAttendanceInputs(employee, attendances, month, year);

        // BR10: Salary can only be calculated for active employees.
        if (employee.getStatus() != Employee.Status.ACTIVE) {
            throw new IllegalStateException("Salary can only be calculated for active employees.");
        }

        AttendanceSummary summary = getAttendanceSummary(employee, attendances, month, year);
        double totalSalary = employee.calculateSalary(
                summary.getWorkingDays(),
                summary.getAbsenceDays(),
                summary.getOvertimeHours());

        StringBuilder output = new StringBuilder();
        output.append(String.format("Total Working Days: %d\n", summary.getWorkingDays()));
        output.append(String.format("Overtime Hours: %.1f\n", summary.getOvertimeHours()));
        output.append(String.format("Absence Days: %d\n", summary.getAbsenceDays()));
        output.append(String.format("Total Salary: %s VND\n", formatVndAmount(totalSalary)));
        return output.toString();
    }

    // 3. Xuất bảng lương ra file
    public void exportSalariesToFile(List<Employee> employees, List<Attendance> attendances, int month, int year,
            String filePath) {
        validateEmployeesAttendanceInputs(employees, attendances, month, year);
        validateFilePath(filePath);

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(String.format("BẢNG LƯƠNG NHÂN VIÊN THÁNG %d/%d\n", month, year));
            writer.write("ID,Name,Role,Working Days,Absence Days,Leave Days,Total Salary\n");

            for (Employee emp : employees) {
                if (emp.getStatus() != Employee.Status.ACTIVE)
                    continue;

                double salary = calculateEmployeeSalary(emp, attendances, month, year);
                AttendanceSummary summary = getAttendanceSummary(emp, attendances, month, year);

                writer.write(String.format("%s,%s,%s,%d,%d,%d,%.2f\n",
                        emp.getId(), emp.getName(), emp.getClass().getSimpleName(), summary.getWorkingDays(),
                        summary.getAbsenceDays(), summary.getLeaveDays(), salary));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to export salaries to file", e);
        }
    }

    private String formatVndAmount(double amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,##0", symbols);
        return formatter.format(amount);
    }

    private AttendanceSummary summarizeAttendance(Employee employee, List<Attendance> attendances, int month, int year) {
        int workingDays = 0;
        int absenceDays = 0;
        int leaveDays = 0;
        double overtimeHours = 0.0;

        for (Attendance attendance : attendances) {
            if (!attendance.getIdEmployee().equals(employee.getId())) {
                continue;
            }
            if (attendance.getDate().getMonthValue() != month || attendance.getDate().getYear() != year) {
                continue;
            }

            if (attendance.getStatus() == Attendance.AttendanceStatus.PRESENT) {
                workingDays++;
                overtimeHours += attendance.getOvertime();  // Overtime only for PRESENT days
            } else if (attendance.getStatus() == Attendance.AttendanceStatus.ABSENT) {
                absenceDays++;
            } else if (attendance.getStatus() == Attendance.AttendanceStatus.LEAVE) {
                leaveDays++;
            }
        }

        return new AttendanceSummary(workingDays, absenceDays, leaveDays, overtimeHours);
    }

    private void validateEmployeeAttendanceInputs(Employee employee, List<Attendance> attendances, int month, int year) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (attendances == null) {
            throw new IllegalArgumentException("Attendance list cannot be null");
        }
        validateMonthYear(month, year);
    }

    private void validateEmployeesAttendanceInputs(List<Employee> employees, List<Attendance> attendances, int month,
            int year) {
        if (employees == null) {
            throw new IllegalArgumentException("Employee list cannot be null");
        }
        if (attendances == null) {
            throw new IllegalArgumentException("Attendance list cannot be null");
        }
        validateMonthYear(month, year);
    }

    private void validateMonthYear(int month, int year) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be greater than 0");
        }
    }

    private void validateFilePath(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be empty");
        }
    }
}
