package service;
    
import entity.Attendance;
import entity.Employee;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SalaryService {

    List<Employee> employeeList;
    List<Attendance> attendanceList;
    // Constructor
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

        int workingDays = 0;
        int absenceDays = 0;
        int overtimeHours = 0;

        for (Attendance a : attendances) {
            if (a.getIdEmployee().equals(employee.getId()) && a.getDate().getMonthValue() == month
                    && a.getDate().getYear() == year) {
                if (a.getStatus() == Attendance.AttendanceStatus.PRESENT) {
                    workingDays++;
                } else if (a.getStatus() == Attendance.AttendanceStatus.ABSENT) {
                    absenceDays++;
                }
                overtimeHours += (int) a.getOvertime();
            }
        }

        return employee.calculateSalary(workingDays, absenceDays, overtimeHours);
    }

    // 2. Xem bảng lương tất cả nhân viên
    public void displayAllSalaries(List<Employee> employees, List<Attendance> attendances, int month, int year) {
        System.out.println(
                "====================================================================================================");
        System.out.printf(" BẢNG LƯƠNG NHÂN VIÊN THÁNG %d/%d\n", month, year);
        System.out.println(
                "====================================================================================================");
        System.out.printf("%-10s | %-20s | %-15s | %-12s | %-12s | %-15s\n",
                "ID", "Name", "Role", "Working Days", "Absence Days", "Total Salary");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");

        for (Employee emp : employees) {
            if (emp.getStatus() != Employee.Status.ACTIVE)
                continue;

            double salary = calculateEmployeeSalary(emp, attendances, month, year);

            // Tính lại workingDays và absenceDays chỉ để hiển thị
            int workingDays = 0, absenceDays = 0;
            for (Attendance a : attendances) {
                if (a.getIdEmployee().equals(emp.getId()) && a.getDate().getMonthValue() == month
                        && a.getDate().getYear() == year) {
                    if (a.getStatus() == Attendance.AttendanceStatus.PRESENT)
                        workingDays++;
                    else if (a.getStatus() == Attendance.AttendanceStatus.ABSENT)
                        absenceDays++;
                }
            }

            System.out.printf("%-10s | %-20s | %-15s | %-12d | %-12d | %-15.2f\n",
                    emp.getId(), emp.getName(), emp.getClass().getSimpleName(), workingDays, absenceDays, salary);
        }
        System.out.println(
                "====================================================================================================");
    }

    // Task B6 — Calculate Salary (Detail for a single employee)
    public void displaySalaryDetail(Employee employee, List<Attendance> attendances, int month, int year) {
        System.out.println("----------- CALCULATE SALARY -----------");
        System.out.printf("Employee ID: %s\n", employee.getId());
        System.out.printf("Month / Year: %d / %d\n", month, year);

        if (employee.getStatus() != Employee.Status.ACTIVE) {
            System.out.println("Error: Salary can only be calculated for active employees.");
            return;
        }

        int workingDays = 0;
        int absenceDays = 0;
        int overtimeHours = 0;

        for (Attendance a : attendances) {
            if (a.getIdEmployee().equals(employee.getId()) && a.getDate().getMonthValue() == month
                    && a.getDate().getYear() == year) {
                if (a.getStatus() == Attendance.AttendanceStatus.PRESENT) {
                    workingDays++;
                } else if (a.getStatus() == Attendance.AttendanceStatus.ABSENT) {
                    absenceDays++;
                }
                overtimeHours += (int) a.getOvertime();
            }
        }

        double totalSalary = employee.calculateSalary(workingDays, absenceDays, overtimeHours);

        System.out.println("Output");
        System.out.println("Salary calculated successfully.");
        System.out.printf("Total Working Days: %d\n", workingDays);
        System.out.printf("Overtime Hours: %d\n", overtimeHours);
        System.out.printf("Absence Days: %d\n", absenceDays);
        System.out.printf("Total Salary: %,.0f VND\n", totalSalary);
    }

    // 3. Xuất bảng lương ra file
    public void exportSalariesToFile(List<Employee> employees, List<Attendance> attendances, int month, int year,
            String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(String.format("BẢNG LƯƠNG NHÂN VIÊN THÁNG %d/%d\n", month, year));
            writer.write("ID,Name,Role,Working Days,Absence Days,Total Salary\n");

            for (Employee emp : employees) {
                if (emp.getStatus() != Employee.Status.ACTIVE)
                    continue;

                double salary = calculateEmployeeSalary(emp, attendances, month, year);

                int workingDays = 0, absenceDays = 0;
                for (Attendance a : attendances) {
                    if (a.getIdEmployee().equals(emp.getId()) && a.getDate().getMonthValue() == month
                            && a.getDate().getYear() == year) {
                        if (a.getStatus() == Attendance.AttendanceStatus.PRESENT)
                            workingDays++;
                        else if (a.getStatus() == Attendance.AttendanceStatus.ABSENT)
                            absenceDays++;
                    }
                }

                writer.write(String.format("%s,%s,%s,%d,%d,%.2f\n",
                        emp.getId(), emp.getName(), emp.getClass().getSimpleName(), workingDays, absenceDays, salary));
            }
            System.out.println("=> Xuất bảng lương ra file thành công: " + filePath);
        } catch (IOException e) {
            System.out.println("=> Lỗi khi xuất file: " + e.getMessage());
        }
    }
}
