
package entity;

// INHERITANCE: FullTimeEmployee extends Employee.
// Reuses shared fields and behavior from the base class.
public class FullTimeEmployee extends Employee {

    // Subclass constructor delegates to base constructor via "super".
    public FullTimeEmployee(String id, String name, String department, double baseSalary, String jobTitle,
            String dateOfJoining, Status status) {
        super(id, name, department, baseSalary, jobTitle, dateOfJoining, status);
    }

    // POLYMORPHISM: override abstract salary calculation from base class.
    // Full-time salary rule uses 80,000 VND per overtime hour.
    @Override
    public double calculateSalary(int workingDays, int absenceDays, double overtimeHours) {
        // Check for negative values
        if (workingDays < 0 || absenceDays < 0 || overtimeHours < 0) {
            throw new IllegalArgumentException("Absence days and overtime hours cannot be negative.");
        } else {
            // Salary = Base + (Overtime Hours * 80,000) - (Absence Days * 100,000)
            double overtimePay = overtimeHours * 80000;
            double absenceDeduction = absenceDays * 100000;
            double finalSalary = getBaseSalary() + overtimePay - absenceDeduction;
            return Math.max(finalSalary, 0);  // Salary cannot be negative
        }
    }

    // POLYMORPHISM: override toString() from base class.
    // Reuses base output and customizes the heading text.
    @Override
    public String toString() {
        return super.toString().replace("Employee Information:", "Full-Time Employee Information:");
    }

}
