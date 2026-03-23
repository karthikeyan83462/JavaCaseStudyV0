package util;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printHeader(String title) {
        clearScreen();
        System.out.println("============================================");
        System.out.println("  " + title);
        System.out.println("============================================");
        System.out.println();
    }

    public static void printDivider() {
        System.out.println("--------------------------------------------");
    }

    public static String input(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int inputInt(String prompt) {
        try {
            String input = input(prompt);
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    public static void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public static void printSuccess(String message) {
        System.out.println("\n✓ " + message);
    }

    public static void printError(String message) {
        System.out.println("\n✗ " + message);
    }

    public static void printInfo(String message) {
        System.out.println("\nℹ " + message);
    }

    public static void printLine(String message) {
        System.out.println(message);
    }

    // ---------- VALIDATION HELPERS ----------

    public static String inputStrongPassword(String message) {
        while (true) {
            String password = ConsoleUtil.input(message);

            boolean hasUpper = password.matches(".*[A-Z].*");
            boolean hasLower = password.matches(".*[a-z].*");
            boolean hasDigit = password.matches(".*\\d.*");
            boolean hasSpecial = password.matches(".*[@#$%!^&*].*");

            if (password.length() >= 8 && hasUpper && hasLower && hasDigit && hasSpecial && !password.contains(" ")) {
                return password;
            }

            ConsoleUtil.printError(
                    "Password must:\n" +
                            "- Be at least 8 characters\n" +
                            "- Contain uppercase and lowercase letters\n" +
                            "- Contain a number\n" +
                            "- Contain a special character (@#$%!^&*)\n" +
                            "- Not contain spaces");
        }
    }

    public static String inputRequired(String message) {
        String input;
        do {
            input = ConsoleUtil.input(message).trim();
            if (input.isEmpty()) {
                ConsoleUtil.printError("This field cannot be empty.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static String inputMinLength(String message, int minLength) {
        String input;
        do {
            input = ConsoleUtil.input(message).trim();
            if (input.length() < minLength) {
                ConsoleUtil.printError("Must be at least " + minLength + " characters.");
            }
        } while (input.length() < minLength);
        return input;
    }

    public static String inputEmail(String message) {
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

    public static String inputPhone(String message) {
        String phone;
        do {
            phone = ConsoleUtil.input(message).trim();
            if (!phone.matches("\\d{10}")) {
                ConsoleUtil.printError("Phone number must be 10 digits.");
            }
        } while (!phone.matches("\\d{10}"));
        return phone;
    }

    public static int inputRole() {
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
