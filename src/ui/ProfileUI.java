package ui;

import model.User;
import service.EmployeeService;
import util.ConsoleUtil;

public class ProfileUI {

    public static void show(User user) {
        ConsoleUtil.printHeader("MY PROFILE");
        
        // Check if the user is an employee or admin/manager
        if (user.getRole().equals("EMPLOYEE")) {
            User emp = EmployeeService.getEmployeeById(user.getUserId());
            
            if (emp == null) {
                ConsoleUtil.printLine("--- User Information ---");
                ConsoleUtil.printLine("User ID: " + user.getUserId());
                ConsoleUtil.printLine("Username: " + user.getUsername());
                ConsoleUtil.printLine("Role: " + user.getRole());
                ConsoleUtil.printError("\nEmployee profile details not found!");
            } else {
                ConsoleUtil.printLine("--- Profile Information ---");
                ConsoleUtil.printLine("Employee ID: " + emp.getUserId());
                ConsoleUtil.printLine("Name: " + emp.getName());
                ConsoleUtil.printLine("Email: " + emp.getEmail());
                ConsoleUtil.printLine("Phone: " + emp.getPhone());
                ConsoleUtil.printLine("Department: " + emp.getDepartment());
                ConsoleUtil.printLine("Designation: " + emp.getDesignation());
                ConsoleUtil.printLine("Skills: " + emp.getSkills());
                ConsoleUtil.printLine("Joining Date: " + emp.getJoiningDate());
                
                System.out.println("\n1. Edit Profile");
                System.out.println("2. Back");
                
                int choice = ConsoleUtil.inputInt("Enter your choice: ");
                if (choice == 1) {
                    editProfile(emp);
                }
            }
        } else {
            ConsoleUtil.printLine("--- User Information ---");
            ConsoleUtil.printLine("User ID: " + user.getUserId());
            ConsoleUtil.printLine("Username: " + user.getUsername());
            ConsoleUtil.printLine("Role: " + user.getRole());
        }
        
        ConsoleUtil.pause();
    }

    private static void editProfile(User emp) {
        ConsoleUtil.printHeader("EDIT PROFILE");
        String phone = ConsoleUtil.inputPhone("Enter new phone (or press Enter to skip): ");
        String skills = ConsoleUtil.input("Enter new skills (or press Enter to skip): ");

        if (!phone.isEmpty()) {
            emp.setPhone(phone);
        }
        if (!skills.isEmpty()) {
            emp.setSkills(skills);
        }

        if (EmployeeService.updateEmployee(emp.getUserId(), emp)) {
            ConsoleUtil.printSuccess("Profile updated successfully!");
        } else {
            ConsoleUtil.printError("Failed to update profile!");
        }
    }
}
