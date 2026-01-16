
package com.project.lab;


public class FullTimeEmployee extends Employee {

    public FullTimeEmployee(String id, String name, String department, double baseSalary, String jobTitle, String dateOfJoining, String status) {
        super(id, name, department, baseSalary, jobTitle, dateOfJoining, status);
    }
    public double calculateSalary(int absenceDays, int overtimeHours) {
        // Lương = Cơ bản + (Giờ OT * 80k) - (Ngày vắng * 100k) 
        return 0;
    }
    public void output() {
        //
    }
}
