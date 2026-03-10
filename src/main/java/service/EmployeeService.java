
package service;

import java.util.*;
import entity.*;
import entity.Employee.Status;

public class EmployeeService {

    private Map<String, Employee> employeeMap;

    // Constructor
    public EmployeeService() {
        this.employeeMap = new HashMap<>();
    }

    public EmployeeService(Map<String, Employee> employeeMap) {
        this.employeeMap = employeeMap;
    }

    // search
    public Employee searchByID(String id) {
        return employeeMap.get(id);
    }

    // create - C
    public void addEmployee(Employee employee) {

        if (employee == null)
            throw new IllegalArgumentException("Employee cannot be null!");

        if (employee.getId() == null || employee.getId().trim().isEmpty())
            throw new IllegalArgumentException("Employee ID cannot be empty!");

        if (employeeMap.containsKey(employee.getId()))
            throw new IllegalArgumentException("ID has existed! Please enter another ID.");

        if (employee.getName() == null || employee.getName().trim().isEmpty())
            throw new IllegalArgumentException("Employee name cannot be empty");

        if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty())
            throw new IllegalArgumentException("Department cannot be empty");

        employeeMap.put(employee.getId(), employee);

        System.out.println("Added successfully!");
    }

    // read - R - lấy 1 employee theo ID
    public Employee getEmployee(String id) {
        return employeeMap.get(id);
    }

    // read - R - lấy tất cả
    public List<Employee> getAllEmp() {
        return new ArrayList<>(employeeMap.values());
    }

    // update - U
    public void updateEmployee(Employee updateEmp) {

        if (!employeeMap.containsKey(updateEmp.getId()))
            throw new IllegalArgumentException("ID not found! Please do again!");

        employeeMap.put(updateEmp.getId(), updateEmp);

        System.out.println("Update successfully!");
    }

    // delete - D
    public void deleteEmployee(String id) {

        Employee emp = employeeMap.get(id);

        if (emp == null)
            throw new IllegalArgumentException("ID not found! Please do again!");

        emp.setStatus(Status.INACTIVE);

        System.out.println("Delete successfully!");
    }
}
