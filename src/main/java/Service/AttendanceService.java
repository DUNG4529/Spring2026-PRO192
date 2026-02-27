package Service;

import utils.Validation;
import entity.Attendance.AttendanceStatus;// sửa nhé
import entity.Employee;
import java.util.Scanner;

public class AttendanceService {

    private EmployeeService empService; // Nhận cả cái service vào

    public AttendanceService(EmployeeService empService) {
        this.empService = empService;
    }
        Scanner sc = new Scanner(System.in);

    public void CheckandEditAttendance(String id) {
        // check id 
        if (Validation.validID(id)) {
            System.out.println("id form is correct");
        } else {
            System.out.println("id form is incorrect");
            return;
        }
        // search coi có id ko
        int index = empService.searchID(id);

        if (index == -1) {
            System.out.println("❌ Not found!");
            return;
        }
        //láy vị trí và sửa điểm danh
        Employee emp = empService.getEmpIndex(index);

        System.out.println("Đang sửa điểm danh cho: " + emp.getName());
        System.out.println("\n----- CHỌN TRẠNG THÁI ĐIỂM DANH MỚI -----");
        System.out.println("1. PRESENT (Có mặt)");
        System.out.println("2. ABSENT (Vắng mặt)");
        System.out.println("3. LATE (Đi muộn)");
        System.out.print("Lựa chọn của bạn: ");
        String choice = sc.nextLine();
        int a = Integer.parseInt(choice);
        switch (a) {
            case 1:
                emp.setAttendance(AttendanceStatus.PRESENT);
                System.out.println("Cập nhật thành công: PRESENT");
                break;
            case 2:
                emp.setAttendance(AttendanceStatus.ABSENT);
                System.out.println("Cập nhật thành công: ABSENT");
                break;
            case 3:
                emp.setAttendance(AttendanceStatus.LEAVE);
                System.out.println("Cập nhật thành công: LEAVE");
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ! Giữ nguyên trạng thái cũ.");
                break;
        }
    }
}
