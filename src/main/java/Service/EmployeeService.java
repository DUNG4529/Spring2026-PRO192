
package Service;

import Model.Employee_Information;
import Model.Status;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private List<Employee_Information> employeeList;

    public EmployeeService() {
        this.employeeList = new ArrayList<>();
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
    public void addEmployee(Employee_Information employee) {
        if (searchID(employee.getId()) != -1)
            throw new IllegalArgumentException("ID has exist! Pls enter another ID");
        employeeList.add(employee);
        System.out.println("Added successfully!");
    }

    // read - R - lay 1 tu id
    public Employee_Information getEmpIndex(int index) {
        if (index >= 0 && index < employeeList.size()) {
            return employeeList.get(index);
        }
        return null;
    }

    // read - R - lay all in4
    public List<Employee_Information> getAllEmp() {
        return this.employeeList;
    }

    // update - U
    public void updateEmployee(Employee_Information updateEmp) {
        int index = searchID(updateEmp.getId());
        if (index != -1) {
            employeeList.set(index, updateEmp);
            System.out.println("Update successfully!");
        } else
            System.out.println("Update failed! Pls do again!");
    }

    // delete - D
    public void deleteEmployee(String id) {
        int index = searchID(id);
        if (index != -1) {
            Employee_Information emp = employeeList.get(index);
            emp.setStatus(Status.INACTIVE);
            System.out.println("Delete successfully!");
        } else
            System.out.println("Delete failed! Pls do agian!");
    }
}
