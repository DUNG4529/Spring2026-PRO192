package service;

import entity.Attendance;
import entity.Employee;

import java.util.List;

public class ReportService {

    private static final double SALARY_EPSILON = 0.0001;

    // Constructor
    public ReportService() {
    }

    // Task B7 — Employees with Low Attendance (BR12)
    // BR12: An employee is considered to have low attendance if the number of
    // Absent days in a selected month exceeds a predefined threshold (e.g. more
    // than 3 days).
    // threshold days: Ngưỡng số ngày vắng mặt để xác định nhân viên có điểm danh thấp (ví dụ: 3 ngày)
    public String generateLowAttendanceReport(List<Employee> employees, List<Attendance> attendances, int month, int year,
            int threshold) {
        StringBuilder output = new StringBuilder();
        output.append("----------- LOW ATTENDANCE REPORT -----------\n");
        boolean found = false;

        for (Employee emp : employees) {
            if (emp.getStatus() != Employee.Status.ACTIVE)
                continue;

            int absentDays = 0;
            for (Attendance a : attendances) {
                if (a.getIdEmployee().equals(emp.getId()) && a.getDate().getMonthValue() == month
                        && a.getDate().getYear() == year) {
                    if (a.getStatus() == Attendance.AttendanceStatus.ABSENT) {
                        absentDays++;
                    }
                }
            }

            if (absentDays > threshold) {
                output.append(String.format("%s %s %d absent days\n", emp.getId(), emp.getName(), absentDays));
                found = true;
            }
        }

        if (!found) {
            output.append("No employees found with low attendance.\n");
        }
        output.append("--------------------------------------------");
        return output.toString();
    }

    // Task B8 — Highest Paid Employees (BR13)
    // BR13: The highest-paid employees are determined based on the total calculated
    // salary for a selected month
    public String generateHighestPaidEmployeesReport(List<Employee> employees, List<Attendance> attendances, int month,
            int year) {
        StringBuilder output = new StringBuilder();
        output.append("----------- HIGHEST PAID EMPLOYEES -----------\n");

        if (employees == null || attendances == null) {
            throw new IllegalArgumentException("Employee list and attendance list cannot be null");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be greater than 0");
        }

        SalaryService salaryService = new SalaryService();
        double maxSalary = Double.NEGATIVE_INFINITY;

        // Find max salary
        for (Employee emp : employees) {
            if (emp.getStatus() != Employee.Status.ACTIVE)
                continue;
            double salary = salaryService.calculateEmployeeSalary(emp, attendances, month, year);
            if (salary > maxSalary) {
                maxSalary = salary;
            }
        }

        // Print employees with max salary
        boolean found = false;
        for (Employee emp : employees) {
            if (emp.getStatus() != Employee.Status.ACTIVE)
                continue;
            double salary = salaryService.calculateEmployeeSalary(emp, attendances, month, year);
            if (Math.abs(salary - maxSalary) < SALARY_EPSILON) {
                output.append(String.format("%s %s %,.0f VND\n", emp.getId(), emp.getName(), salary));
                found = true;
            }
        }

        if (!found) {
            output.append("No valid salary data found for this month.\n");
        }
        output.append("---------------------------------------------");
        return output.toString();
    }
}
