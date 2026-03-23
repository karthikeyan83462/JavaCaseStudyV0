package ui;

import model.User;
import service.AuthService;
import util.ConsoleUtil;

import java.util.regex.Pattern;

public class AuthUI {

    // ---------- LOGIN ----------
    public static void login() {
        ConsoleUtil.printHeader("LOGIN");

        String username = inputRequired("Enter username: ");
        String password = inputRequired("Enter password: ");

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

        String username = inputMinLength("Enter username: ", 4);
        String password = inputMinLength("Enter password: ", 6);

        int roleChoice = inputRole();
        String role = roleChoice == 2 ? "MANAGER" : "EMPLOYEE";

        String name = inputRequired("Enter full name: ");
        String email = inputEmail("Enter email: ");
        String phone = inputPhone("Enter phone: ");
        String department = inputRequired("Enter department: ");

        String designation = "";
        String supervisorId = "";

        if (role.equals("EMPLOYEE")) {
            designation = inputRequired("Enter designation: ");
            supervisorId = inputRequired("Enter Manager ID: ");
        }

        String skills = inputRequired("Enter skills (semicolon separated): ");

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

    // ---------- VALIDATION HELPERS ----------

    private static String inputRequired(String message) {
        String input;
        do {
            input = ConsoleUtil.input(message).trim();
            if (input.isEmpty()) {
                ConsoleUtil.printError("This field cannot be empty.");
            }
        } while (input.isEmpty());
        return input;
    }

    private static String inputMinLength(String message, int minLength) {
        String input;
        do {
            input = ConsoleUtil.input(message).trim();
            if (input.length() < minLength) {
                ConsoleUtil.printError("Must be at least " + minLength + " characters.");
            }
        } while (input.length() < minLength);
        return input;
    }

    private static String inputEmail(String message) {
        String email;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        do {
            email = ConsoleUtil.input(message).trim();
            if (!Pattern.matches(emailRegex, email)) {
                ConsoleUtil.printError("Invalid email format.");
            }
        } while (!Pattern.matches(emailRegex, email));
        return email;
    }

    private static String inputPhone(String message) {
        String phone;
        do {
            phone = ConsoleUtil.input(message).trim();
            if (!phone.matches("\\d{10}")) {
                ConsoleUtil.printError("Phone number must be 10 digits.");
            }
        } while (!phone.matches("\\d{10}"));
        return phone;
    }

    private static int inputRole() {
        int choice;
        do {
            System.out.println("\nSelect Role:");
            System.out.println("1. Employee");
            System.out.println("2. Manager");
            choice = ConsoleUtil.inputInt("Enter choice: ");
            if (choice != 1 && choice != 2) {
                ConsoleUtil.printError("Invalid choice. Please select 1 or 2.");
            }
        } while (choice != 1 && choice != 2);
        return choice;
    }
}