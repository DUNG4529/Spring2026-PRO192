/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 * Enum định nghĩa các trạng thái điểm danh
 */
enum AttendanceStatus {
    PRESENT("Present"), // Có mặt
    ABSENT("Absent"), // Vắng mặt
    LATE("Late"), // Đi muộn
    EARLY_LEAVE("Early Leave"); // Về sớm

    private String displayName;

    AttendanceStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

/**
 * Class Attendance dùng để lưu thông tin điểm danh của nhân viên
 * Bao gồm: mã nhân viên, ngày điểm danh, trạng thái và giờ tăng ca
 */
public class Attendance {
    private String idEmployee; // Mã nhân viên
    private LocalDate date; // Ngày điểm danh
    private AttendanceStatus status; // Trạng thái điểm danh
    private double overtime; // Số giờ làm thêm (overtime)

    /// =========================
    /// KHỞI TẠO DỮ LIỆU
    /// =========================

    // Constructor không tham số
    public Attendance() {
    }

    // Constructor đầy đủ tham số
    public Attendance(String idEmployee, LocalDate date, AttendanceStatus status, double overtime) {
        this.idEmployee = idEmployee;
        this.date = date;
        this.status = status;
        this.overtime = overtime;
    }

    /// =========================
    /// CẬP NHẬT VÀ TRUY XUẤT DỮ LIỆU (GETTER & SETTER)
    /// =========================

    /**
     * Lấy mã nhân viên
     */
    public String getIdEmployee() {
        return idEmployee;
    }

    /**
     * Cập nhật mã nhân viên
     */
    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    /**
     * Lấy ngày điểm danh
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Cập nhật ngày điểm danh
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Lấy trạng thái điểm danh
     */
    public AttendanceStatus getStatus() {
        return status;
    }

    /**
     * Cập nhật trạng thái điểm danh
     */
    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    /**
     * Lấy số giờ tăng ca
     */
    public double getOvertime() {
        return overtime;
    }

    /**
     * Cập nhật số giờ tăng ca
     */
    public void setOvertime(double overtime) {
        this.overtime = overtime;
    }

}
