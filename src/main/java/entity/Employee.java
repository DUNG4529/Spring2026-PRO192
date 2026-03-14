
package entity;

public abstract class Employee {

    /**
     * Employment status for an employee
     */
    public static enum Status {
        ACTIVE("Active"),
        INACTIVE("Inactive");

        private final String label;

        Status(String label) {
            this.label = label;
        }

        public String getStatus() {
            return label;
        }
    }

    private final String id; // ID is unique and cannot be changed once set (final)

    private String name; // Full name (not empty)
    private String department; // Department
    protected double baseSalary; // Base salary
    private String jobTitle; // Job title
    private String dateOfJoining; // Date of joining (format: "dd/MM/yyyy")
    private Status status; // Employment status (Active/Inactive)

    // Constructor 
    public Employee(String id, String name, String department, double baseSalary, String jobTitle, String dateOfJoining,
            Status status) {
        // BR1: ID must be unique and immutable once set (final)
        // BR2: Name and department cannot be empty
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
            }
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }

        this.id = id;
        this.name = name;
        this.department = department;
        this.baseSalary = baseSalary;
        this.jobTitle = jobTitle;
        this.dateOfJoining = dateOfJoining;
        this.status = status;
        
    }
    // Getter & Setter for all properties except ID
    // Get employee ID
    public String getId() {
        return id;
    }

    // Get full name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        this.name = name;
    }

    // Get department
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }

        this.department = department;
    }

    // Get base salary
    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    // Get job title
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    // Get date of joining
    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    // Get employment status
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    // ABSTRACTION: salary calculation is declared as an abstract method.
    // Subclasses must provide their own implementation.
    public abstract double calculateSalary(int workingDays, int absenceDays, double overtimeHours);

    // toString to display employee information
    @Override
    public String toString() {
        return String.format(
                "Employee Information:\n" +
                        "ID: %s\n" +
                        "Name: %s\n" +
                        "Department: %s\n" +
                        "Base Salary: %.2f\n" +
                        "Job Title: %s\n" +
                        "Date of Joining: %s\n" +
                        "Status: %s",
                id, name, department, baseSalary, jobTitle, dateOfJoining, status.getStatus());
    }

}
