
package Model;

/**
 * Trạng thái làm việc của nhân viên
 */
enum EmployeeStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String displayName;

    EmployeeStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

public abstract class Employee {
    private String id; // (Unique)
    private String name; // (Not empty)
    private String department; // (Not empty)
    protected double baseSalary;
    private String jobTitle;
    private String dateOfJoining;
    private EmployeeStatus status; // (Active/Inactive)

    public Employee(String id, String name, String department, double baseSalary, String jobTitle, String dateOfJoining,
            EmployeeStatus status) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.baseSalary = baseSalary;
        this.jobTitle = jobTitle;
        this.dateOfJoining = dateOfJoining;
        this.status = status;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public abstract double calculateSalary(int absenceDays, int overtimeHours);

    public void output() {
        System.out.printf("%-5s | %-20s | %-15s | %-15s | %-10s\n", id, name, department, jobTitle,
                status.getDisplayName());
    }
}
