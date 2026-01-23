
package Model;

/**
 * Trạng thái làm việc của nhân viên
 */
enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

public abstract class Employee {
    private String id;              // Mã nhân viên (Not empty, unique)
    private String name;            // Họ và tên (Not empty)
    private String department;      // Phòng ban
    protected double baseSalary;    // Lương cơ bản
    private String jobTitle;        // Chức vụ
    private String dateOfJoining;   // Ngày vào làm (Định dạng: "dd/MM/yyyy")
    private Status status;          // Trạng thái làm việc (Active/Inactive)

    public Employee(String id, String name,
                        String department, double baseSalary,
                            String jobTitle, String dateOfJoining,
                                Status status) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.baseSalary = baseSalary;
        this.jobTitle = jobTitle;
        this.dateOfJoining = dateOfJoining;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public abstract double calculateSalary(int absenceDays, int overtimeHours);

    // output employee information
    public String output() {
        return String.format(
                "Employee Information:\n" +
                        "  ID            : %s\n" +
                        "  Name          : %s\n" +
                        "  Department    : %s\n" +
                        "  Base Salary   : %.2f\n" +
                        "  Job Title     : %s\n" +
                        "  Date Of Join  : %s\n" +
                        "  Status        : %s",
                id, name, department, baseSalary, jobTitle, dateOfJoining, status);
    }

}
