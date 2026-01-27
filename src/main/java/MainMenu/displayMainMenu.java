package MainMenu;

// Imports Validation Classes
import Validation.ValidationInput;

// Imports Model Classes
import Model.Employee_Information;
import Model.Attendance_Information;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;
import Model.Payroll;

// Imports Service Classes
import Service.EmployeeService;
import Service.AttendanceService;
import Service.PayrollService;

// Imports Validation Classes
import Validation.ValidationInput;

// Other Imports
import java.util.Scanner;

public class displayMainMenu {

	// Service instances (reuse within menu loop)
	private static final EmployeeService employeeService = new EmployeeService();

	// Scanner instance
	private static final Scanner scanner = new Scanner(System.in);

	// Manage Employees
	public static void manageEmployees() {

		int choice;
		Boolean exit = false;

		while (!exit) {
			// Display Employee Management Menu
			System.out.println("====================================================");
			System.out.println("============= Employee Management Menu =============");
			System.out.println("====================================================");

			// Input Employee Choices
			System.out.println("1. Add Employee");
			System.out.println("2. Display Employees");
			System.out.println("3. Update Employee");
			System.out.println("4. Delete Employee");
			System.out.println("5. Search Employee");
			System.out.println("0. Exit to Main Menu");
			System.out.print("Please select an option (0-5): ");

			// Validate Input Choice
			do {
				System.out.print("Enter your choice (0-5): ");
				choice = Integer.parseInt(scanner.nextLine());
				if (choice < 0 || choice > 5) {
					System.out.println("Invalid choice. Please select a valid option.");
				}
			} while (choice < 0 || choice > 5);

			// Perform actions based on user choice
			switch (choice) {
				case 1:// ========================================================================================================
					System.out.println("======================== Adding Employee ========================");
					// Input ID for Employee
					String idEmployee;
					do {
						System.out.print("Enter ID (Format: XX000000): ");
						idEmployee = scanner.nextLine();
						if (!ValidationInput.validID(idEmployee)) {
							System.out.println("ID not valid! Please enter again.");
							idEmployee = null; // retry
						}
					} while (idEmployee == null);

					// Input Name for Employee
					String name;
					do {
						System.out.print("Enter Name: ");
						name = scanner.nextLine();
						if (!ValidationInput.validName(name)) {
							System.out.println("Name not valid! Please enter again.");
							name = null; // retry
						}
					} while (name == null);

					// Employee Department
					System.out.print("Enter Department: ");
					String department;
					do {
						department = scanner.nextLine();
						if (!ValidationInput.validString(department)) {
							System.out.println("Department not valid! Please enter again.");
							department = null; // retry
						}
					} while (department == null);

					// Employee Base Salary
					double baseSalary = 0;
					boolean checkSalary = false;
					do {
						System.out.print("Enter Base Salary: ");
						baseSalary = Double.parseDouble(scanner.nextLine());
						checkSalary = ValidationInput.validSalary(baseSalary);
						if (!checkSalary) {
							System.out.println("Salary not valid! Please enter again.");
						}
					} while (!checkSalary);

					// Employee Job Title
					System.out.print("Enter Job Title: ");
					String jobTitle;
					do {
						jobTitle = scanner.nextLine();
						if (!ValidationInput.validString(jobTitle)) {
							System.out.println("Job Title not valid! Please enter again.");
							jobTitle = null; // retry
						}
					} while (jobTitle == null);

					// Employee Date Of Joining
					System.out.print("Enter Date Of Joining (dd/MM/yyyy): ");
					String dateOfJoining;
					do {
						dateOfJoining = scanner.nextLine();
						if (!ValidationInput.validDate(dateOfJoining)) {
							System.out.println("Date not valid! Please enter again.");
							dateOfJoining = null; // retry
						}
					} while (dateOfJoining == null);

					// Employee Status
					Employee_Information.Status status = null;
					do {
						System.out.print("Status (1-Active, 2-Inactive): ");
						int statusChoice = Integer.parseInt(scanner.nextLine());
						if (statusChoice == 1) {
							status = Employee_Information.Status.ACTIVE;
						} else if (statusChoice == 2) {
							status = Employee_Information.Status.INACTIVE;
						} else {
							System.out.println("Please enter 1 or 2.");
						}
					} while (status == null);

					// Employee Type
					int typeChoice;
					Boolean validType;
					do {
						System.out.print("Employee Type (1-FullTime, 2-PartTime): ");
						typeChoice = Integer.parseInt(scanner.nextLine());
						validType = (typeChoice == 1 || typeChoice == 2);
						if (!validType) {
							System.out.println("Please enter 1 or 2.");
						}
					} while (!validType);

					// Create Employee Object based on type
					Employee_Information newEmp;
					if (typeChoice == 1) {
						newEmp = new FullTimeEmployee(idEmployee, name, department, baseSalary, jobTitle, dateOfJoining,
								status);
					} else {
						newEmp = new PartTimeEmployee(idEmployee, name, department, baseSalary, jobTitle, dateOfJoining,
								status);
					}

					// Add Employee to the system
					employeeService.addEmployee(newEmp);
					break;
				case 2: // ========================================================================================================
					System.out.println("======================== Displaying Employees ========================");

					if (employeeService.getAllEmp().isEmpty()) {
						System.out.println("No employees to display.");
						break;
					}

					System.out.println("--- Employee List ---");
					for (Employee_Information emp : employeeService.getAllEmp()) {
						System.out.println(emp.output());
						System.out.println("----------------------");
					}
					break;
				case 3: // ========================================================================================================
					System.out.println("======================== Updating Employee ========================");
					// Enter ID employee to update
					System.out.print("Enter Employee ID to update: ");
					String updateID = scanner.nextLine();

					if (!ValidationInput.validID(updateID)) {
						System.out.println("ID not valid! Please enter again.");
						break;
					}
					int empIndex = employeeService.searchID(updateID);
					if (empIndex == -1) {
						System.out.println("Employee with ID " + updateID + " not found.");
						break;
					}
					Employee_Information existingEmp = employeeService.getEmpIndex(empIndex);
					
					System.out.println("Which information do you want to update for employee ID " + updateID + "?");
					System.out.println("1. Name");
					System.out.println("2. Department");
					System.out.println("3. Base Salary");
					System.out.println("4. Job Title");
					System.out.println("5. Date Of Joining");
					System.out.println("6. Status");
					System.out.println("0. Cancel");
					System.out.print("Please select an option (0-6): ");
					
					int userChoice = Integer.parseInt(scanner.nextLine());

					switch (userChoice) {
						case 0:
							System.out.println("Update complete.");
							break;
						case 1:
							// Update Name
							String newName;
							do {
								System.out.print("Enter new Name: ");
								newName = scanner.nextLine();
								if (!ValidationInput.validName(newName)) {
									System.out.println("Name not valid! Please enter again.");
									newName = null; // retry
								}
							} while (newName == null);
							existingEmp.setName(newName);
							break;
						case 2:
							// Update Department
							System.out.print("Enter new Department: ");
							String newDepartment;
							do {
								newDepartment = scanner.nextLine();
								if (!ValidationInput.validString(newDepartment)) {
									System.out.println("Department not valid! Please enter again.");
									newDepartment = null; // retry
								}
							} while (newDepartment == null);
							existingEmp.setDepartment(newDepartment);
							break;
						case 3:
							// Update Base Salary
							double newBaseSalary = 0;
							boolean checkNewSalary = false;
							do {
								System.out.print("Enter new Base Salary: ");
								newBaseSalary = Double.parseDouble(scanner.nextLine());
								checkNewSalary = ValidationInput.validSalary(newBaseSalary);
								if (!checkNewSalary) {
									System.out.println("Salary not valid! Please enter again.");
								}
							} while (!checkNewSalary);
							existingEmp.setBaseSalary(newBaseSalary);
							break;
						case 4:
							// Update Job Title
							System.out.print("Enter new Job Title: ");
							String newJobTitle;
							do {
								newJobTitle = scanner.nextLine();
								if (!ValidationInput.validString(newJobTitle)) {
									System.out.println("Job Title not valid! Please enter again.");
									newJobTitle = null; // retry
								}
							} while (newJobTitle == null);
							existingEmp.setJobTitle(newJobTitle);
							break;
						case 5:
							// Update Date Of Joining
							String newDateOfJoining;
							do {
								System.out.print("Enter new Date Of Joining (dd/MM/yyyy): ");
								newDateOfJoining = scanner.nextLine();
								if (!ValidationInput.validDate(newDateOfJoining)) {
									System.out.println("Date not valid! Please enter again.");
									newDateOfJoining = null; // retry
								}
							} while (newDateOfJoining == null);
							existingEmp.setDateOfJoining(newDateOfJoining);
							break;
						case 6:
							// Update Status
							System.out.print("Status Choice: ");
							System.out.println("	1. ACTIVE");
							System.out.println("	2. INACTIVE");
							System.out.println("Please select an option (1-2): ");
							int statusChoice = Integer.parseInt(scanner.nextLine());
							if (statusChoice == 1) {
								existingEmp.setStatus(Employee_Information.Status.ACTIVE);
							} else if (statusChoice == 2) {
								existingEmp.setStatus(Employee_Information.Status.INACTIVE);
							} else {
								System.out.println("Invalid choice. Status not updated.");
							}
							break;
						default:
							System.out.println("Invalid choice. No updates made.");
							break;
					}

					// Save updated employee information
					employeeService.updateEmployee(existingEmp);
					break;
				case 4: // ========================================================================================================
					System.out.println("======================== Deleting Employee ========================");
					// Enter ID employee to delete
					System.out.print("Enter Employee ID to delete: ");
					do {
						System.out.print("Enter ID (Format: XX000000): ");
						updateID = scanner.nextLine();
						if (!ValidationInput.validID(updateID)) {
							System.out.println("ID not valid! Please enter again.");
							updateID = null; // retry
						}
					} while (updateID == null);

					// Delete Employee
					employeeService.deleteEmployee(updateID);
					break;
				case 5: // ========================================================================================================
					System.out.println("======================== Searching Employee ========================");
					
					// Enter ID employee to search
					System.out.print("Enter Employee ID to search: ");
					
					// Check valid ID
					String searchID;
					do {
						System.out.print("Enter ID (Format: XX000000): ");
						searchID = scanner.nextLine();
						if (!ValidationInput.validID(searchID)) {
							System.out.println("ID not valid! Please enter again.");
							searchID = null; // retry
						}
					} while (searchID == null);

					// Search Employee
					int searchIndex = employeeService.searchID(searchID);
					if (searchIndex != -1) {
						Employee_Information foundEmp = employeeService.getEmpIndex(searchIndex);
						System.out.println("Employee found:");
						System.out.println(foundEmp.output());
					} else {
						System.out.println("Employee with ID " + searchID + " not found.");
					}
					break;
				case 0: // ========================================================================================================
					System.out.println("======================== Exiting to Main Menu ========================");
					exit = true;
					break;
				default: // ========================================================================================================
					System.out.println("Invalid choice. Please select a valid option.");
			}
		}
	}

	// Manage Attendance
	public static void manageAttendance() {
		System.out.println("====================================================");
		System.out.println("============ Attendance Management Menu ============");
		System.out.println("====================================================");
	}

	// Calculate Salaries

	public static void calculateSalaries() {
		System.out.println("====================================================");
		System.out.println("============= Salary Calculation Menu ==============");
		System.out.println("====================================================");
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("===== Employee Management System =====");
		System.out.println("1. Manage Employees");
		System.out.println("2. Manage Attendance");
		System.out.println("3. Calculate Salaries");
		System.out.println("4. Exit");
		System.out.print("Please select an option (1-4): ");

		int choice = Integer.parseInt(scanner.nextLine());

		switch (choice) {
			case 1:
				System.out.println("Managing Employees...");
				manageEmployees();
				break;
			case 2:
				System.out.println("Managing Attendance...");
				manageAttendance();
				break;
			case 3:
				System.out.println("Calculating Salaries...");
				calculateSalaries();
				break;
			case 4:
				System.out.println("Exiting the system. Goodbye!");
				break;
			default:
				System.out.println("Invalid choice. Please select a valid option.");
		}
		scanner.close();
	}
}
