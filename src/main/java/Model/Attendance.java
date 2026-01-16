/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.time.LocalDate;

/**
 * Class Attendance dùng để lưu thông tin điểm danh của nhân viên
 * Bao gồm: mã nhân viên, ngày điểm danh, trạng thái và giờ tăng ca
 */
public class Attendance {
    private String idEmployee;  // Mã nhân viên
    private LocalDate date;     // Ngày điểm danh 
    private String status;      // Trạng thái điểm danh (ví dụ: "Present", "Absent", "Late", ...)
    private double overtime;    // Số giờ làm thêm (overtime)
    
    /// =========================
    /// KHỞI TẠO DỮ LIỆU
    /// =========================

    // Constructor không tham số
    public Attendance() {
    }
    // Constructor đầy đủ tham số
    public Attendance(String idEmployee, LocalDate date, String status, double overtime) {
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
    public String getStatus() {
        return status;
    }

    /**
     * Cập nhật trạng thái điểm danh
     */
    public void setStatus(String status) {
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
