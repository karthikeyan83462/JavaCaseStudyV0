package ui;

import model.User;
import service.EmployeeService;
import util.ConsoleUtil;
import java.util.List;

public class EmployeeUI {

    public static void show(User user) {
        if (!user.getRole().equals("ADMIN") && !user.getRole().equals("MANAGER")) {
            ConsoleUtil.printError("Access denied!");
            ConsoleUtil.pause();
            return;
        }

        while (true) {
            ConsoleUtil.printHeader("EMPLOYEE MANAGEMENT");
            System.out.println("1. Add New Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. View Employee Details");
            System.out.println("4. Edit Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Search Employees by Skill");
            System.out.println("7. View Team Structure");
            System.out.println("8. Back");
            System.out.println();

            int choice = ConsoleUtil.inputInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    viewEmployeeDetails();
                    break;
                case 4:
                    editEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    searchBySkill();
                    break;
                case 7:
                    viewTeamStructure();
                    break;
                case 8:
                    return;
                default:
                    ConsoleUtil.printError("Invalid choice!");
                    ConsoleUtil.pause();
            }
        }
    }

    private static void addEmployee() {
        ConsoleUtil.printHeader("ADD NEW EMPLOYEE");
        String name = ConsoleUtil.inputRequired("Enter name: ");
        String email = ConsoleUtil.inputEmail("Enter email: ");
        String phone = ConsoleUtil.inputPhone("Enter phone: ");
        String department = ConsoleUtil.inputDepartment("Enter Department: ");
        String designation = ConsoleUtil.inputDesignation("Enter Designation: ");
        String skills = ConsoleUtil.inputRequired("Enter skills (comma separated): ");
        String supervisorId = ConsoleUtil.input("Enter supervisor ID (or leave blank): ");

        if (EmployeeService.addEmployee(name, email, phone, department, designation, skills, supervisorId)) {
            ConsoleUtil.printSuccess("Employee added successfully!");
        } else {
            ConsoleUtil.printError("Failed to add employee!");
        }
        ConsoleUtil.pause();
    }

    private static void viewAllEmployees() {
        ConsoleUtil.printHeader("ALL EMPLOYEES");
        List<User> employees = EmployeeService.getAllEmployees();

        if (employees.isEmpty()) {
            ConsoleUtil.printInfo("No employees found!");
        } else {
            ConsoleUtil.printLine("ID | Name | Email | Department | Designation");
            ConsoleUtil.printDivider();
            for (User emp : employees) {
                System.out.println(emp.getUserId() + " | " + emp.getName() + " | " + emp.getEmail() + " | " +
                                 emp.getDepartment() + " | " + emp.getDesignation());
            }
        }
        ConsoleUtil.pause();
    }

    private static void viewEmployeeDetails() {
        ConsoleUtil.printHeader("VIEW EMPLOYEE DETAILS");
        String empId = ConsoleUtil.inputRequired("Enter employee ID: ");
        User emp = EmployeeService.getEmployeeById(empId);

        if (emp == null) {
            ConsoleUtil.printError("Employee not found!");
        } else {
            ConsoleUtil.printLine("\n--- Employee Details ---");
            ConsoleUtil.printLine("ID: " + emp.getUserId());
            ConsoleUtil.printLine("Name: " + emp.getName());
            ConsoleUtil.printLine("Email: " + emp.getEmail());
            ConsoleUtil.printLine("Phone: " + emp.getPhone());
            ConsoleUtil.printLine("Department: " + emp.getDepartment());
            ConsoleUtil.printLine("Designation: " + emp.getDesignation());
            ConsoleUtil.printLine("Skills: " + emp.getSkills());
            ConsoleUtil.printLine("Supervisor ID: " + emp.getSupervisorId());
            ConsoleUtil.printLine("Joining Date: " + emp.getJoiningDate());
        }
        ConsoleUtil.pause();
    }

    private static void editEmployee() {
        ConsoleUtil.printHeader("EDIT EMPLOYEE");
        String empId = ConsoleUtil.inputRequired("Enter employee ID: ");
        User emp = EmployeeService.getEmployeeById(empId);
        
        if (emp == null) {
            ConsoleUtil.printError("Employee not found!");
        } else {
            String name = ConsoleUtil.inputRequired("Enter new name (or press Enter to skip): ");
            String email = ConsoleUtil.inputEmail("Enter new email (or press Enter to skip): ");
            String phone = ConsoleUtil.input("Enter new phone (or press Enter to skip): ");
            String department = ConsoleUtil.inputDepartment("Enter new department (or press Enter to skip): ");
            String designation = ConsoleUtil.inputDesignation("Enter new designation (or press Enter to skip): ");
            String skills = ConsoleUtil.inputRequired("Enter new skills (or press Enter to skip): ");

            if (!name.isEmpty()) emp.setName(name);
            if (!email.isEmpty()) emp.setEmail(email);
            if (!phone.isEmpty()) emp.setPhone(phone);
            if (!department.isEmpty()) emp.setDepartment(department);
            if (!designation.isEmpty()) emp.setDesignation(designation);
            if (!skills.isEmpty()) emp.setSkills(skills);

            if (EmployeeService.updateEmployee(empId, emp)) {
                ConsoleUtil.printSuccess("Employee updated successfully!");
            } else {
                ConsoleUtil.printError("Failed to update employee!");
            }
        }
        ConsoleUtil.pause();
    }

    private static void deleteEmployee() {
        ConsoleUtil.printHeader("DELETE EMPLOYEE");
        String empId = ConsoleUtil.inputRequired("Enter employee ID: ");

        if (EmployeeService.deleteEmployee(empId)) {
            ConsoleUtil.printSuccess("Employee deleted successfully!");
        } else {
            ConsoleUtil.printError("Failed to delete employee!");
        }
        ConsoleUtil.pause();
    }

    private static void searchBySkill() {
        ConsoleUtil.printHeader("SEARCH EMPLOYEES BY SKILL");
        String skill = ConsoleUtil.inputRequired("Enter skill: ");
        List<User> employees = EmployeeService.searchBySkill(skill);

        if (employees.isEmpty()) {
            ConsoleUtil.printInfo("No employees found with this skill!");
        } else {
            ConsoleUtil.printLine("ID | Name | Email | Skills");
            ConsoleUtil.printDivider();
            for (User emp : employees) {
                System.out.println(emp.getUserId() + " | " + emp.getName() + " | " + emp.getEmail() + " | " + emp.getSkills());
            }
        }
        ConsoleUtil.pause();
    }

    private static void viewTeamStructure() {
        ConsoleUtil.printHeader("VIEW TEAM STRUCTURE");
        String supervisorId = ConsoleUtil.input("Enter supervisor ID: ");
        List<User> team = EmployeeService.getTeamMembers(supervisorId);

        if (team.isEmpty()) {
            ConsoleUtil.printInfo("No team members found!");
        } else {
            ConsoleUtil.printLine("Team Members of " + supervisorId + ":");
            ConsoleUtil.printDivider();
            for (User emp : team) {
                System.out.println("- " + emp.getName() + " (" + emp.getUserId() + ") - " + emp.getDesignation());
            }
        }
        ConsoleUtil.pause();
    }
}
