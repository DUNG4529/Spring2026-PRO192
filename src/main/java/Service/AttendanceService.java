package service;

import entity.Attendance;
import entity.Attendance.AttendanceStatus;
import entity.Employee;
import utils.Validation;

import java.time.LocalDate;
import java.util.List;

public class AttendanceService {

    private final List<Attendance> attendanceList;
    private final EmployeeService empService;

    public AttendanceService(List<Attendance> attendanceList, List<Employee> employeeList) {
        this.attendanceList = attendanceList;
        this.empService = new EmployeeService(employeeList);
    }

    public AttendanceService(EmployeeService empService) {
        this.attendanceList = new java.util.ArrayList<>();
        this.empService = empService;
    }

    public void CheckandEditAttendance(String id) {
        markPresent(id);
    }

    public boolean markPresent(String id) {
        return markAttendance(id, AttendanceStatus.PRESENT);
    }

    public boolean markAbsent(String id) {
        return markAttendance(id, AttendanceStatus.ABSENT);
    }

    public boolean markLeave(String id) {
        return markAttendance(id, AttendanceStatus.LEAVE);
    }

    private boolean markAttendance(String id, AttendanceStatus status) {
        if (!Validation.validID(id)) {
            return false;
        }

        int index = empService.searchID(id);
        if (index == -1) {
            return false;
        }

        Employee emp = empService.getEmpIndex(index);
        if (emp == null) {
            return false;
        }

        emp.setAttendance(status);
        return true;
    }

    public List<Attendance> getAllAttendance() {
        return attendanceList;
    }

    public int searchAttendance(String idEmployee, LocalDate date) {
        for (int i = 0; i < attendanceList.size(); i++) {
            Attendance attendance = attendanceList.get(i);
            if (attendance.getIdEmployee().equalsIgnoreCase(idEmployee)
                    && attendance.getDate().equals(date)) {
                return i;
            }
        }
        return -1;
    }

    public void addAttendance(Attendance attendance) {
        if (attendance == null) {
            throw new IllegalArgumentException("Attendance must not be null");
        }

        if (empService.searchID(attendance.getIdEmployee()) == -1) {
            throw new IllegalArgumentException("Employee does not exist");
        }

        if (searchAttendance(attendance.getIdEmployee(), attendance.getDate()) != -1) {
            throw new IllegalArgumentException("Attendance already exists for this employee and date");
        }

        attendance.setStatus(AttendanceStatus.PRESENT);
        attendanceList.add(attendance);
    }

    public void updateAttendance(String idEmployee, LocalDate date, String status, double overtime) {
        int index = searchAttendance(idEmployee, date);
        if (index == -1) {
            return;
        }

        Attendance attendance = attendanceList.get(index);
        attendance.setStatus(AttendanceStatus.PRESENT);
        attendance.setOvertime(overtime);
    }

    public void deleteAttendance(String idEmployee, LocalDate date) {
        int index = searchAttendance(idEmployee, date);
        if (index == -1) {
            return;
        }
        attendanceList.remove(index);
    }

    public List<Attendance> getAttendanceByEmployeeId(String idEmployee) {
        List<Attendance> result = new java.util.ArrayList<>();
        for (Attendance attendance : attendanceList) {
            if (attendance.getIdEmployee().equalsIgnoreCase(idEmployee)) {
                result.add(attendance);
            }
        }
        return result;
    }
}
