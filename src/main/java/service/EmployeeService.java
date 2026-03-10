
package service;

import java.util.*;
import entity.*;

public class EmployeeService {
    
    private List<Employee> employeeList;
    // Constructor
    public EmployeeService() {
        this.employeeList = new ArrayList<>();
    }
    public EmployeeService(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
    // search
    public int searchID(String id) {
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getId().equalsIgnoreCase(id))
                return i;
        }
        return -1;
    }

    // create - C
    public void addEmployee(Employee employee) {
        // BR1: ID phải là duy nhất và không được thay đổi sau khi đã thiết lập (final)
        if (employee == null)
            throw new IllegalArgumentException("Employee cannot be null!");
        
        if (employee.getId() == null || employee.getId().trim().isEmpty())
            throw new IllegalArgumentException("Employee ID cannot be empty!");

        if (searchID(employee.getId()) != -1)
            throw new IllegalArgumentException("ID has existed! Please enter another ID.");

        // BR2: Tên và phòng ban không được để trống
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }

        if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
        employeeList.add(employee);
        System.out.println("Added successfully!");
    }

    // read - R - lay 1 tu id
    public Employee getEmpIndex(int index) {
        if (index >= 0 && index < employeeList.size()) {
            return employeeList.get(index);
        }
        return null;
    }

    // read - R - lay all in4
    public List<Employee> getAllEmp() {
        return Collections.unmodifiableList(employeeList); // Trả về danh sách không thể sửa đổi để bảo vệ dữ liệu gốc
    }

    // update - U
    public void updateEmployee(Employee updateEmp) {
        int index = searchID(updateEmp.getId());
        if (index != -1) {
            employeeList.set(index, updateEmp);
            System.out.println("Update successfully!");
        } else
            throw new IllegalArgumentException("ID not found! Please do again!");
    }

    // delete - D
    public void deleteEmployee(String id) {
        int index = searchID(id);
        if (index != -1) {
            Employee emp = employeeList.get(index);
            emp.setStatus(Employee.Status.INACTIVE); // set trạng thái thành INACTIVE
            System.out.println("Delete successfully!");
        } else
            throw new IllegalArgumentException("ID not found! Please do again!");
    }
}
