
package Model;

public class FullTimeEmployee extends Employee_Information {

    public FullTimeEmployee(String id, String name, String department, double baseSalary, String jobTitle,
            String dateOfJoining, Status status) {
        super(id, name, department, baseSalary, jobTitle, dateOfJoining, status);
    }

    public double calculateSalary(int absenceDays, int overtimeHours) {
        // Check for negative values
        if (absenceDays < 0 || overtimeHours < 0) {
            throw new IllegalArgumentException("Absence days and overtime hours cannot be negative.");
        } else {
            // Lương = Cơ bản + (Giờ OT * 80k) - (Ngày vắng * 100k)
            double overtimePay = overtimeHours * 80000;
            double absenceDeduction = absenceDays * 100000;
            return getBaseSalary() + overtimePay - absenceDeduction;
        }
    }

}
