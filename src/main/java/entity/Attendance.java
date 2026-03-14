/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Attendance entity stores employee attendance information
 * including employee ID, attendance date, status, and overtime hours.
 */
public class Attendance {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final String idEmployee; // Employee ID (immutable after creation)
    private LocalDate date; // Attendance date
    private AttendanceStatus status; // Attendance status
    private double overtime; // Overtime hours

    public enum AttendanceStatus {
        PRESENT("Present"),
        ABSENT("Absent"),
        LEAVE("Leave");

        private String displayName;

        AttendanceStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
    /// =========================
    /// INITIALIZATION
    /// =========================

    // Full constructor
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
        if (status != AttendanceStatus.PRESENT && overtime > 0) {
            throw new IllegalArgumentException("Overtime is only allowed for PRESENT status");
        }
        this.idEmployee = idEmployee;
        this.date = date;
        this.status = status;
        this.overtime = overtime;
    }

    /// =========================
    /// ACCESSORS AND MUTATORS (GETTER & SETTER)
    /// =========================

    /**
    * Returns employee ID
     */
    public String getIdEmployee() {
        return idEmployee;
    }

    /**
    * Returns attendance date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
    * Updates attendance date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
    * Returns attendance status
     */
    public AttendanceStatus getStatus() {
        return status;
    }

    /**
    * Updates attendance status
     */
    public void setStatus(AttendanceStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Attendance status cannot be null");
        }
        this.status = status;
        if (status != AttendanceStatus.PRESENT) {
            this.overtime = 0.0;
        }
    }

    /**
    * Returns overtime hours
     */
    public double getOvertime() {
        return overtime;
    }

    /**
    * Updates overtime hours
     */
    public void setOvertime(double overtime) {
        if (overtime < 0) {
            throw new IllegalArgumentException("Overtime cannot be negative");
        }
        if (this.status != AttendanceStatus.PRESENT && overtime > 0) {
            throw new IllegalArgumentException("Overtime is only allowed for PRESENT status");
        }
        this.overtime = overtime;
    }

    @Override
    public String toString() {
        return String.format("Date: %s, Status: %s, Overtime: %.2f",
                date.format(DATE_FORMATTER), status.getDisplayName(), overtime);
    }

}
