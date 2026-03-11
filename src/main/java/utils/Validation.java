package utils;

import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validation {

    // 1. Kiểm tra ID nhân viên (Mẫu: 2 chữ hoa + 6 số)
    public static boolean validID(String id) {
        String regexID = "^[A-Z]{2}\\d{6}$";
        return Pattern.matches(regexID, id);
    }

    // 2. Kiểm tra Tên (Chứa chữ cái Unicode, khoảng trắng, 3-50 ký tự - hỗ trợ tên Việt)
    public static boolean validName(String name) {
        String regexName = "^[\\p{L} ]{3,50}$";  // \p{L} matches any Unicode letter
        return Pattern.matches(regexName, name);
    }

    // 3. Kiểm tra Lương hoặc các số tiền (Phải >= 0)
    public static boolean validSalary(double salary) {
        return salary >= 0;
    }

    // 4. Kiểm tra Chuỗi không được rỗng (Dùng cho Department, Job Title - BR2)
    public static boolean validString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // 5. Kiểm tra Ngày tháng (Ép check lịch thực tế tránh ngày ảo như 30/02/2023)
    public static boolean validDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // 6. CẢI TIẾN: Kiểm tra Menu số (Dùng cho Đạt check số người dùng nhập ở UI)
    // Ví dụ: Nhập trạng thái 1-3, hoặc Main Menu từ 1-5
    public static boolean validChoice(int choice, int min, int max) {
        return choice >= min && choice <= max;
    }

    // 7. Kiểm tra Trạng thái chấm công (Bắt buộc giữ để bảo vệ lõi dữ liệu theo BR5)
    public static boolean validAttendanceStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        String s = status.trim().toLowerCase();
        return s.equals("present") || s.equals("absent") || s.equals("leave");
    }

    // 8. Kiểm tra chuỗi nhập vào có phải là số thực (Double) hợp lệ không
    public static boolean validNumber(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 9. Hàm in thông báo lỗi chuẩn UI
    public static void showError(String message) {
        System.out.println("Fail: " + message);
    }
}