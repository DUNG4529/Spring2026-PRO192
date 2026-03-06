
package entity;

import entity.Attendance.AttendanceStatus;// sửa nhé 

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

    private String id; // Mã nhân viên (Not empty, unique)
    private String name; // Họ và tên (Not empty)
    private String department; // Phòng ban
    protected double baseSalary; // Lương cơ bản
    private String jobTitle; // Chức vụ
    private String dateOfJoining; // Ngày vào làm (Định dạng: "dd/MM/yyyy")
    private Status status; // Trạng thái làm việc (Active/Inactive)
    private AttendanceStatus attendance = AttendanceStatus.ABSENT; // Trạng thái attendance (sửa ở đây nha)
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
    // Getter và Setter cho các thuộc tính

    // Lấy mã nhân viên
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Lấy họ và tên
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Lấy phòng ban
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Lấy lương cơ bản
    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    // Lấy chức vụ
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    // Lấy ngày vào làm
    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    // Lấy trạng thái làm việc
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // sửa ở đây nhé
    public AttendanceStatus getAttendance() {
        return attendance;
    }

    public void setAttendance(AttendanceStatus attendance) {
        this.attendance = attendance;
    }
    
     // TÍNH TRỪU TƯỢNG (Abstraction): Khai báo phương thức tính lương là "abstract"
    // Phương thức này chỉ có tên và tham số, không có thân hàm (body).
    // Bắt buộc các lớp con (kế thừa) phải định nghĩa lại (override) phương thức
    // này.
    public abstract double calculateSalary(int workingDays, int absenceDays, int overtimeHours);

    // ToString để hiển thị thông tin nhân viên
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
