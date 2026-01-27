
package Model;

public class PartTimeEmployee extends Employee_Information {

    public PartTimeEmployee(String id, String name, String department, double baseSalary, String jobTitle,
            String dateOfJoining, Status status) {
        super(id, name, department, baseSalary, jobTitle, dateOfJoining, status);
    }

    public double calculateSalary(int absenceDays, int overtimeHours) {
        // Lương = Cơ bản + (Giờ OT * 50k) - (Ngày vắng * 100k)
        if (absenceDays < 0 || overtimeHours < 0) {
            throw new IllegalArgumentException("Absence days and overtime hours cannot be negative.");
        } else {
            double overtimePay = overtimeHours * 50000;
            double absenceDeduction = absenceDays * 100000;
            return getBaseSalary() + overtimePay - absenceDeduction;
        }
    }
    
}
