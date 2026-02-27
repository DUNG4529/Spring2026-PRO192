
package entity;

// TÍNH KẾ THỪA (Inheritance): PartTimeEmployee extends (kế thừa) lớp cha Employee.
// Tái sử dụng lại các thuộc tính và phương thức của lớp cha.
public class PartTimeEmployee extends Employee {

    // CONSTRUCTOR của lớp con gọi constructor của lớp cha thông qua từ khóa "super"
    public PartTimeEmployee(String id, String name, String department, double baseSalary, String jobTitle,
            String dateOfJoining, Status status) {
        super(id, name, department, baseSalary, jobTitle, dateOfJoining, status);
    }

    // TÍNH ĐA HÌNH (Polymorphism): Ghi đè (Override) lại phương thức abstract từ
    // lớp cha.
    // Lớp PartTime thực hiện logic tính lương riêng: OT tính 50k.
    @Override
    public double calculateSalary(int workingDays, int absenceDays, int overtimeHours) {
        // Lương = Cơ bản + (Giờ OT * 50k) - (Ngày vắng * 100k)
        if (workingDays < 0 || absenceDays < 0 || overtimeHours < 0) {
            throw new IllegalArgumentException("Absence days and overtime hours cannot be negative.");
        } else {
            double overtimePay = overtimeHours * 50000;
            double absenceDeduction = absenceDays * 100000;
            return getBaseSalary() + overtimePay - absenceDeduction;
        }
    }

    // TÍNH ĐA HÌNH (Polymorphism): Ghi đè phương thức output() của lớp cha
    // Gọi ngược hàm ở lớp cha (super.output()) và nối đổi lại text cho phù hợp.
    @Override
    public String output() {
        return super.output().replace("Employee Information:", "Part-Time Employee Information:");
    }

}
