
package Model;

public class PartTimeEmployee extends Employee {

    public PartTimeEmployee(String id, String name, String department, double baseSalary, String jobTitle,
            String dateOfJoining, Status status) {
        super(id, name, department, baseSalary, jobTitle, dateOfJoining, status);
    }

    public double calculateSalary(int absenceDays, int overtimeHours) {
        // Lương = Cơ bản + (Giờ OT * 50k) - (Ngày vắng * 100k)
        return 0;
    }

    
}
