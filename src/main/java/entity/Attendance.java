/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDate;

/**
 * Class Attendance dùng để lưu thông tin điểm danh của nhân viên
 * Bao gồm: mã nhân viên, ngày điểm danh, trạng thái và giờ tăng ca
 */
public class Attendance {
    private final String idEmployee; // Mã nhân viên (không đổi sau khi tạo)
    private LocalDate date; // Ngày điểm danh
    private AttendanceStatus status; // Trạng thái điểm danh
    private double overtime; // Số giờ làm thêm (overtime)

    public enum AttendanceStatus {
        PRESENT("Present"), // Có mặt
        ABSENT("Absent"), // Vắng mặt
        LEAVE("Leave"); // Nghỉ phép

        private String displayName;

        AttendanceStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
    /// =========================
    /// KHỞI TẠO DỮ LIỆU
    /// =========================

    // Constructor đầy đủ tham số
    public Attendance(String idEmployee, LocalDate date, AttendanceStatus status, double overtime) {
        if (idEmployee == null || idEmployee.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Attendance date cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Attendance status cannot be null");
        }
        if (overtime < 0) {
            throw new IllegalArgumentException("Overtime cannot be negative");
        }
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
        if (status == null) {
            throw new IllegalArgumentException("Attendance status cannot be null");
        }
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
        if (overtime < 0) {
            throw new IllegalArgumentException("Overtime cannot be negative");
        }
        this.overtime = overtime;
    }

}
