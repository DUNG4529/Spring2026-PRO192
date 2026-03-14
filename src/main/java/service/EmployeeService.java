
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
        if (isBlank(id)) {
            return null;
        }
        return employeeMap.get(id);
    }

    // create - C
    public void addEmployee(Employee employee) {
        validateEmployeeForSave(employee);

        if (employeeMap.containsKey(employee.getId()))
            throw new IllegalArgumentException("ID has existed! Please enter another ID.");

        employeeMap.put(employee.getId(), employee);
    }

    // read - R - get one employee by ID
    public Employee getEmployee(String id) {
        if (isBlank(id)) {
            return null;
        }
        return employeeMap.get(id);
    }

    // read - R - get all employees
    public List<Employee> getAllEmp() {
        return new ArrayList<>(employeeMap.values());
    }

    public void clearAll() {
        employeeMap.clear();
    }

    // update - U
    public void updateEmployee(Employee updateEmp) {
        validateEmployeeForSave(updateEmp);

        if (!employeeMap.containsKey(updateEmp.getId()))
            throw new IllegalArgumentException("ID not found! Please do again!");

        employeeMap.put(updateEmp.getId(), updateEmp);
    }

    // delete - D
    public void deleteEmployee(String id) {
        validateEmployeeId(id);

        Employee emp = employeeMap.get(id);

        if (emp == null)
            throw new IllegalArgumentException("ID not found! Please do again!");

        emp.setStatus(Status.INACTIVE);
    }

    private void validateEmployeeForSave(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null!");
        }
        validateEmployeeId(employee.getId());
        if (isBlank(employee.getName())) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        if (isBlank(employee.getDepartment())) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
    }

    private void validateEmployeeId(String id) {
        if (isBlank(id)) {
            throw new IllegalArgumentException("Employee ID cannot be empty");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
