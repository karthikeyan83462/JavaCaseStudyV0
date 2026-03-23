package ui;

import model.User;
import service.AuthService;
import util.ConsoleUtil;

public class AuthUI {

    // ---------- LOGIN ----------
    public static void login() {
        ConsoleUtil.printHeader("LOGIN");

        String username = ConsoleUtil.inputRequired("Enter username: ");
        String password = ConsoleUtil.inputStrongPassword("Enter password: ");

        User user = AuthService.login(username, password);
        if (user != null) {
            ConsoleUtil.printSuccess("Login successful!");
        } else {
            ConsoleUtil.printError("Invalid credentials!");
        }
        ConsoleUtil.pause();
    }

    // ---------- REGISTER ----------
    public static void register() {
        ConsoleUtil.printHeader("REGISTER");

        String username = ConsoleUtil.inputRequired("Enter username: ");
        String password = ConsoleUtil.inputStrongPasswordWithConfirm();

        int roleChoice = ConsoleUtil.inputRole();
        String role = roleChoice == 2 ? "MANAGER" : "EMPLOYEE";

        String name = ConsoleUtil.inputRequired("Enter full name: ");
        String email = ConsoleUtil.inputEmail("Enter email: ");
        String phone = ConsoleUtil.inputPhone("Enter phone: ");
        String department = ConsoleUtil.inputDepartment("Enter department: ");

        String designation = "";
        String supervisorId = "";

        if (role.equals("EMPLOYEE")) {
            designation = ConsoleUtil.inputDesignation("Enter designation: ");
            supervisorId = ConsoleUtil.inputRequired("Enter Manager ID: ");
        }

        String skills = ConsoleUtil.inputRequired("Enter skills (semicolon separated): ");

        boolean success = AuthService.registerWithDetails(
                username, password, role, name, email, phone,
                department, designation, skills, supervisorId
        );

        if (success) {
            ConsoleUtil.printSuccess("Registration successful! You can now login.");
        } else {
            ConsoleUtil.printError("Username already exists!");
        }
        ConsoleUtil.pause();
    }

}