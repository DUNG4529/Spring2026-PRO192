package service;

import entity.Attendance;
import entity.Employee;

import java.util.List;

public class ReportService {

    // Constructor
    List<Employee> employeeList;
    List<Attendance> attendanceList;

    public ReportService(List<Employee> employeeList, List<Attendance> attendanceList) {
        this.employeeList = employeeList;
        this.attendanceList = attendanceList;
    }

    // Task B7 — Employees with Low Attendance (BR12)
    // BR12: An employee is considered to have low attendance if the number of
    // Absent days in a selected month exceeds a predefined threshold (e.g. more
    // than 3 days).
    // threshold days: Số ngày đi làm tối thiểu trong 1 tháng 
    public void generateLowAttendanceReport(List<Employee> employees, List<Attendance> attendances, int month, int year,
            int threshold) {
        System.out.println("----------- LOW ATTENDANCE REPORT -----------");
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
                System.out.printf("%s %s %d absent days\n", emp.getId(), emp.getName(), absentDays);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No employees found with low attendance.");
        }
        System.out.println("--------------------------------------------");
    }

    // Task B8 — Highest Paid Employees (BR13)
    // BR13: The highest-paid employees are determined based on the total calculated
    // salary for a selected month
    public void generateHighestPaidEmployeesReport(List<Employee> employees, List<Attendance> attendances, int month,
            int year) {
        System.out.println("----------- HIGHEST PAID EMPLOYEES -----------");

        SalaryService salaryService = new SalaryService(employees, attendances);
        double maxSalary = 0;

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
        if (maxSalary > 0) {
            for (Employee emp : employees) {
                if (emp.getStatus() != Employee.Status.ACTIVE)
                    continue;
                double salary = salaryService.calculateEmployeeSalary(emp, attendances, month, year);
                if (salary == maxSalary) {
                    System.out.printf("%s %s %,.0f VND\n", emp.getId(), emp.getName(), salary);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No valid salary data found for this month.");
        }
        System.out.println("---------------------------------------------");
    }
}
