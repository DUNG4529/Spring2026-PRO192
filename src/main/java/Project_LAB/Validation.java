package Project_LAB;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Tra Giang 
 */


public class Validation {

    // Định dạng ngày chung cho toàn project, sửa 1 chỗ là ăn hết
    private static final String DATE_PATTERN = "dd/MM/yyyy";

    /**
     * Check chuỗi rỗng.
     * Dùng cho các trường bắt buộc như: Name, Department.
     */
    public static boolean checkString(String s) {
        if (s == null || s.trim().isEmpty()) {
            showError("Thông tin này không được để trống!");
            return false;
        }
        return true;
    }

    /**
     * Check số dương (> 0).
     * Dùng cho: Lương cơ bản, Giờ OT.
     */
    public static boolean checkPositiveNumber(double number) {
        if (number <= 0) {
            showError("Giá trị phải lớn hơn 0!");
            return false;
        }
        return true;
    }

    /**
     * Validate ngày tháng (dd/MM/yyyy).
     * SetLenient(false) để chặn các ngày vô lý (ví dụ: 30/02).
     */
    public static boolean checkDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        sdf.setLenient(false); // Bắt buộc đúng chuẩn ngày thực tế

        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            showError("Ngày không hợp lệ! Vui lòng nhập theo định dạng: " + DATE_PATTERN);
            return false;
        }
    }

    /**
     * Check trạng thái chấm công.
     * Chỉ chấp nhận: Present, Absent, Leave (không phân biệt hoa thường).
     */
    public static boolean checkStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            showError("Trạng thái không được để trống!");
            return false;
        }

        // Chuẩn hóa về chữ thường để so sánh cho dễ
        String s = status.trim().toLowerCase();

        if (s.equals("present") || s.equals("absent") || s.equals("leave")) {
            return true;
        } else {
            showError("Trạng thái chỉ được là: Present, Absent hoặc Leave.");
            return false;
        }
    }

    // Hàm tiện ích để in lỗi màu đỏ (nếu IDE hỗ trợ) hoặc in thường
    private static void showError(String msg) {
        System.err.println(">>> Lỗi: " + msg); // System.err in chữ màu đỏ trong console
    }
}