
package Model;


public abstract class Employee {
    private String id; // (Unique)
    private String name; // (Not empty)
    private String department; //(Not empty)
    protected double baseSalary; 
    private String jobTitle ;
    private String dateOfJoining ;
    private String status; // (Active/Inactive) 

    public Employee(String id, String name, String department, double baseSalary, String jobTitle, String dateOfJoining, String status) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.baseSalary = baseSalary;
        this.jobTitle = jobTitle;
        this.dateOfJoining = dateOfJoining;
        this.status = status;
    }
    public abstract double calculateSalary(int absenceDays, int overtimeHours);
    public void output() {
        System.out.printf("%-5s | %-20s | %-15s | %-15s | %-10s\n", id, name, department, jobTitle, status);
    }
}
    