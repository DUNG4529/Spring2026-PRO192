
package entity;

// TÍNH KẾ THỪA (Inheritance): FullTimeEmployee kế thừa (extends) từ lớp cha Employee.
// Tái sử dụng lại toàn bộ các thuộc tính (id, tên, ...) và các phương thức getter/setter của lớp cha.
public class FullTimeEmployee extends Employee {

    // CONSTRUCTOR của lớp con gọi constructor của lớp cha thông qua từ khóa "super"
    public FullTimeEmployee(String id, String name, String department, double baseSalary, String jobTitle,
            String dateOfJoining, Status status) {
        super(id, name, department, baseSalary, jobTitle, dateOfJoining, status);
    }

    // TÍNH ĐA HÌNH (Polymorphism): Ghi đè (Override) lại phương thức abstract từ
    // lớp cha.
    // Lớp FullTime thực hiện logic tính lương riêng: OT tính 80k.
    @Override
    public double calculateSalary(int workingDays, int absenceDays, int overtimeHours) {
        // Check for negative values
        if (workingDays < 0 || absenceDays < 0 || overtimeHours < 0) {
            throw new IllegalArgumentException("Absence days and overtime hours cannot be negative.");
        } else {
            // Lương = Cơ bản + (Giờ OT * 80k) - (Ngày vắng * 100k)
            double overtimePay = overtimeHours * 80000;
            double absenceDeduction = absenceDays * 100000;
            return getBaseSalary() + overtimePay - absenceDeduction;
        }
    }

    // TÍNH ĐA HÌNH (Polymorphism): Ghi đè phương thức toString() của lớp cha
    // Gọi ngược hàm ở lớp cha (super.toString()) và nối đổi lại text cho phù hợp.
    @Override
    public String toString() {
        return super.toString().replace("Employee Information:", "Full-Time Employee Information:");
    }

}
