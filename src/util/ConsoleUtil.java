package util;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUtil {
    // ---------- PREDEFINED VALUES ----------

    private static final String[] DEPARTMENTS = {
            "Engineering",
            "Human Resources",
            "Finance",
            "Sales",
            "Marketing",
            "Operations",
            "IT Support"
    };

    private static final String[] DESIGNATIONS = {
            "Software Engineer",
            "Senior Engineer",
            "Team Lead",
            "Analyst",
            "Executive",
            "Intern"
    };

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
        System.out.println("\n " + message);
    }

    public static void printError(String message) {
        System.out.println("\n " + message);
    }

    public static void printInfo(String message) {
        System.out.println("\n " + message);
    }

    public static void printLine(String message) {
        System.out.println(message);
    }

    // ---------- VALIDATION HELPERS ----------

    public static String inputStrongPassword(String message) {
        while (true) {
            String password = ConsoleUtil.input(message);

            boolean hasUpper = false;
            boolean hasLower = false;
            boolean hasDigit = false;
            boolean hasSpecial = false;

            for (char ch : password.toCharArray()) {
                if (Character.isUpperCase(ch)) {
                    hasUpper = true;
                } else if (Character.isLowerCase(ch)) {
                    hasLower = true;
                } else if (Character.isDigit(ch)) {
                    hasDigit = true;
                } else if ("@#$%!^&*".indexOf(ch) >= 0) {
                    hasSpecial = true;
                } else if (Character.isWhitespace(ch)) {
                    hasUpper = hasLower = hasDigit = hasSpecial = false;
                    break; // spaces not allowed
                }
            }

            if (password.length() >= 8 && hasUpper && hasLower && hasDigit && hasSpecial) {
                return password;
            }

            ConsoleUtil.printError(
                    "Password must:\n" +
                            "- Be at least 8 characters\n" +
                            "- Contain uppercase and lowercase letters\n" +
                            "- Contain a number\n" +
                            "- Contain a special character (@#$%!^&*)\n" +
                            "- Not contain spaces\n");
        }
    }

    public static String inputDepartment(String prompt) {
        int choice;

        while (true) {
            System.out.println("\n" + prompt);
            for (int i = 0; i < DEPARTMENTS.length; i++) {
                System.out.println((i + 1) + ". " + DEPARTMENTS[i]);
            }

            choice = inputInt("Enter choice: ");

            if (choice >= 1 && choice <= DEPARTMENTS.length) {
                return DEPARTMENTS[choice - 1];
            }

            printError("Invalid department choice. Please select a valid number.");
        }
    }

    public static String inputDesignation(String prompt) {
        int choice;

        while (true) {
            System.out.println("\n" + prompt);
            for (int i = 0; i < DESIGNATIONS.length; i++) {
                System.out.println((i + 1) + ". " + DESIGNATIONS[i]);
            }

            choice = inputInt("Enter choice: ");

            if (choice >= 1 && choice <= DESIGNATIONS.length) {
                return DESIGNATIONS[choice - 1];
            }

            printError("Invalid designation choice. Please select a valid number.");
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

    public static String inputStrongPasswordWithConfirm() {
        while (true) {
            String password = ConsoleUtil.input("Enter password: ");

            boolean hasUpper = false;
            boolean hasLower = false;
            boolean hasDigit = false;
            boolean hasSpecial = false;
            boolean hasSpace = false;

            for (char ch : password.toCharArray()) {
                if (Character.isUpperCase(ch)) {
                    hasUpper = true;
                } else if (Character.isLowerCase(ch)) {
                    hasLower = true;
                } else if (Character.isDigit(ch)) {
                    hasDigit = true;
                } else if ("@#$%!^&*".indexOf(ch) >= 0) {
                    hasSpecial = true;
                } else if (Character.isWhitespace(ch)) {
                    hasSpace = true;
                    break;
                }
            }

            if (password.length() < 8) {
                ConsoleUtil.printError("Password must be at least 8 characters.\n");
                continue;
            }
            if (hasSpace) {
                ConsoleUtil.printError("Password must not contain spaces.");
                continue;
            }
            if (!hasUpper || !hasLower) {
                ConsoleUtil.printError("Password must contain uppercase and lowercase letters.");
                continue;
            }
            if (!hasDigit) {
                ConsoleUtil.printError("Password must contain at least one number.");
                continue;
            }
            if (!hasSpecial) {
                ConsoleUtil.printError("Password must contain a special character (@#$%!^&*).");
                continue;
            }

            String confirmPassword = ConsoleUtil.input("Confirm password: ");

            if (!password.equals(confirmPassword)) {
                ConsoleUtil.printError("Passwords do not match.");
                continue;
            }

            return password;
        }
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

    public static String inputUsername(String message) {
        while (true) {
            String username = ConsoleUtil.input(message);

            if (username.isEmpty()) {
                ConsoleUtil.printError("Username cannot be empty.");
                continue;
            }

            boolean hasLetter = false;
            boolean hasSpace = false;

            for (char ch : username.toCharArray()) {
                if (Character.isLetter(ch)) {
                    hasLetter = true;
                } else if (Character.isWhitespace(ch)) {
                    hasSpace = true;
                    break;
                }
            }

            if (hasSpace) {
                ConsoleUtil.printError("Username must not contain spaces.");
                continue;
            }

            if (!hasLetter) {
                ConsoleUtil.printError("Username cannot contain only numbers.");
                continue;
            }

            return username;
        }
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
