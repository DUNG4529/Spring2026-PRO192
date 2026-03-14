
package entity;

// INHERITANCE: PartTimeEmployee extends Employee.
// Reuses shared fields and behavior from the base class.
public class PartTimeEmployee extends Employee {

    // Subclass constructor delegates to base constructor via "super".
    public PartTimeEmployee(String id, String name, String department, double baseSalary, String jobTitle,
            String dateOfJoining, Status status) {
        super(id, name, department, baseSalary, jobTitle, dateOfJoining, status);
    }

    // POLYMORPHISM: override abstract salary calculation from base class.
    // Part-time salary rule uses 50,000 VND per overtime hour.
    @Override
    public double calculateSalary(int workingDays, int absenceDays, double overtimeHours) {
        // Salary = Base + (Overtime Hours * 50,000) - (Absence Days * 100,000)
        if (workingDays < 0 || absenceDays < 0 || overtimeHours < 0) {
            throw new IllegalArgumentException("Absence days and overtime hours cannot be negative.");
        } else {
            double overtimePay = overtimeHours * 50000;
            double absenceDeduction = absenceDays * 100000;
            double finalSalary = getBaseSalary() + overtimePay - absenceDeduction;
            return Math.max(finalSalary, 0);  // Salary cannot be negative
        }
    }

    // POLYMORPHISM: override toString() from base class.
    // Reuses base output and customizes the heading text.
    @Override
    public String toString() {
        return super.toString().replace("Employee Information:", "Part-Time Employee Information:");
    }

}
