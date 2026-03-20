package ui;

import model.User;
import service.AuthService;
import util.ConsoleUtil;

public class AuthUI {

    public static void login() {
        ConsoleUtil.printHeader("LOGIN");
        String username = ConsoleUtil.input("Enter username: ");
        String password = ConsoleUtil.input("Enter password: ");
        
        User user = AuthService.login(username, password);
        if (user != null) {
            ConsoleUtil.printSuccess("Login successful!");
            ConsoleUtil.pause();
        } else {
            ConsoleUtil.printError("Invalid credentials!");
            ConsoleUtil.pause();
        }
    }

    public static void register() {
        ConsoleUtil.printHeader("REGISTER");
        String username = ConsoleUtil.input("Enter username: ");
        String password = ConsoleUtil.input("Enter password: ");
        
        System.out.println("\nSelect Role:");
        System.out.println("1. Employee");
        System.out.println("2. Manager");
        
        int roleChoice = ConsoleUtil.inputInt("Enter choice: ");
        String role = roleChoice == 2 ? "MANAGER" : "EMPLOYEE";
        
        // Collect details for both Employee and Manager
        String name = ConsoleUtil.input("Enter full name: ");
        String email = ConsoleUtil.input("Enter email: ");
        String phone = ConsoleUtil.input("Enter phone: ");
        String department = ConsoleUtil.input("Enter department: ");
        String designation = role.equals("MANAGER") ? "" : ConsoleUtil.input("Enter designation: ");
        String skills = ConsoleUtil.input("Enter skills (semicolon separated): ");
        String supervisorId = role.equals("MANAGER") ? "" : ConsoleUtil.input("Enter Manager ID (for employees): ");
        
        if (AuthService.registerWithDetails(username, password, role, name, email, phone, department, designation, skills, supervisorId)) {
            ConsoleUtil.printSuccess("Registration successful! You can now login.");
        } else {
            ConsoleUtil.printError("Username already exists!");
        }
        ConsoleUtil.pause();
    }
}
