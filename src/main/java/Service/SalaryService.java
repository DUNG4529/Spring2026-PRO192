package service;

import java.util.List;
import entity.Attendance;
import entity.Employee;

public class SalaryService {
    
    private List<Employee> employees;
    private List<Attendance> attendances;

    public SalaryService(List<Employee> employees, List<Attendance> attendances) {
        this.employees = employees;
        this.attendances = attendances;
    }

}