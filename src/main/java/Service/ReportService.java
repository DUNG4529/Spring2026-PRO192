package service;

import java.util.List;
import entity.*;


public class ReportService {

    private List<Employee> employees;
    private List<Attendance> attendances;

    // Constructor: Nhận dữ liệu gốc từ HRManager thông qua Dependency Injection
    public ReportService(List<Employee> employees, List<Attendance> attendances) {
        this.employees = employees;
        this.attendances = attendances;
    }
    // BR12: Báo cáo nhân viên có số ngày vắng mặt nhiều nhất [9]
    
    private static void mostAbsentReport() {
        System.out.println("Generating report for most absent employees...");
        // Implementation of the most absent report generation would go here
    }
}
