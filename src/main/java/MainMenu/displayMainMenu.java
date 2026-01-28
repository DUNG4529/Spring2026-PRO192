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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class displayMainMenu {

	// Service instances (reuse within menu loop)
	private static final EmployeeService employeeService = new EmployeeService();

	// Scanner instance
	private static final Scanner scanner = new Scanner(System.in);

	// Check valid ID
	public static String checkID(String prompt) {
		String id;
		while (true) {
			System.out.print(prompt);
			id = scanner.nextLine();
			// Validate ID
			if (ValidationInput.validID(id)) {
				return id;
			}
			// Invalid ID message
			System.out.println("ID not valid! Please enter again.");
		}
	}

	// check valid Name
	public static String checkName(String prompt) {
		String name;
		while (true) {
			System.out.print(prompt);
			name = scanner.nextLine();
			// Validate Name
			if (ValidationInput.validName(name)) {
				return name;
			}
			// Invalid Name message
			System.out.println("Name not valid! Please enter again.");
		}
	}

	// check valid String
	public static String checkString(String prompt) {
		String input;
		while (true) {
			System.out.print(prompt);
			input = scanner.nextLine();
			// Validate String
			if (ValidationInput.validString(input)) {
				return input;
			}
			// Invalid String message
			System.out.println("Input not valid! Please enter again.");
		}
	}

	// valid number input
	public static double checkDouble(String prompt) {
		double number;
		while (true) {
			System.out.print(prompt);
			try {
				number = Double.parseDouble(scanner.nextLine());
				return number;
			} catch (NumberFormatException e) {
				System.out.println("Invalid number! Please enter again.");
			}
		}
	}

	// valid salary input
	public static double checkSalary(String prompt) {
		double salary;
		while (true) {
			System.out.print(prompt);
			salary = Double.parseDouble(scanner.nextLine());
			// Validate Salary
			if (ValidationInput.validSalary(salary)) {
				return salary;
			}
			// Invalid Salary message
			System.out.println("Salary not valid! Please enter again.");
		}
	}

	// valid date input
	public static String checkDate(String prompt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dateInput;
		while (true) {
			System.out.print(prompt);
			dateInput = scanner.nextLine();
			try {
				LocalDate.parse(dateInput, formatter);
				return dateInput;
			} catch (DateTimeParseException e) {
				System.out.println("Date not valid! Please enter again.");
			}
		}
	}

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
					String idEmployee = checkID("Enter ID (Format: XX000000): ");

					// Input Name for Employee
					String name = checkName("Enter Name: ");

					// Employee Department
					String department = checkString("Enter Department: ");

					// Employee Base Salary
					double baseSalary = checkDouble("Enter Base Salary: ");
					while (!ValidationInput.validSalary(baseSalary)) {
						System.out.println("Salary not valid! Please enter again.");
						baseSalary = checkDouble("Enter Base Salary: ");
					}

					// Employee Job Title
					String jobTitle = checkString("Enter Job Title: ");

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
						if (emp.getStatus() == Employee_Information.Status.INACTIVE) {
							continue; // Skip inactive employees
						}
						System.out.println(emp.output());
						System.out.println("----------------------");
					}
					break;
				case 3: // ========================================================================================================
					System.out.println("======================== Updating Employee ========================");
					// Enter ID employee to update
					String updateID = checkID("Enter Employee ID to update: ");
					// Find Employee Index
					int empIndexCheck;
					while (true) {
						empIndexCheck = employeeService.searchID(updateID);
						if (empIndexCheck == -1) {
							System.out.println("Employee with ID " + updateID + " not found. Please enter again.");
							updateID = checkID("Enter Employee ID to update: ");
						} else {
							break; // Employee found
						}
					}

					Employee_Information existingEmp = employeeService.getEmpIndex(empIndexCheck);

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
							String newName = checkName("Enter new Name: ");
							existingEmp.setName(newName);
							break;
						case 2:
							// Update Department
							String newDepartment = checkName("Enter new Department: ");
							existingEmp.setDepartment(newDepartment);
							break;
						case 3:
							// Update Base Salary
							double newBaseSalary = checkSalary("Enter new Base Salary: ");
							existingEmp.setBaseSalary(newBaseSalary);
							break;
						case 4:
							// Update Job Title
							String newJobTitle = checkName("Enter new Job Title: ");
							existingEmp.setJobTitle(newJobTitle);
							break;
						case 5:
							// Update Date Of Joining
							String newDateOfJoining = checkDate("Enter new Date Of Joining (dd/MM/yyyy): ");
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
					updateID = checkID("Enter Employee ID to delete: ");
					// Find Employee Index
					while (true) {
						empIndexCheck = employeeService.searchID(updateID);
						if (empIndexCheck == -1) {
							System.out.println("Employee with ID " + updateID + " not found. Please enter again.");
							updateID = checkID("Enter Employee ID to delete: ");
						} else {
							break; // Employee found
						}
					}
					// Delete Employee
					employeeService.deleteEmployee(updateID);
					break;
				case 5: // ========================================================================================================
					System.out.println("======================== Searching Employee ========================");

					// Enter ID employee to search
					String searchID = checkID("Enter Employee ID to search: ");
					// Search Employee
					empIndexCheck = employeeService.searchID(searchID);
					while (true) {
						if (empIndexCheck == -1) {
							System.out.println("Employee with ID " + searchID + " not found. Please enter again.");
							searchID = checkID("Enter Employee ID to search: ");
							empIndexCheck = employeeService.searchID(searchID);
						} else {
							break; // Employee found
						}
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
		System.out.println("0. Exit");
		System.out.print("Please select an option (0-3): ");

		int choice = Integer.parseInt(scanner.nextLine());
		Boolean checkChoice = true;
		do {
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
				case 0:
					System.out.println("Exiting the system. Goodbye!");
					checkChoice = false;
					break;
				default:
					System.out.println("Invalid choice. Please select a valid option.");
			}
		} while (checkChoice);

		// Close scanner
		scanner.close();
	}
}
