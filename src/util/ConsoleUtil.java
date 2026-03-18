package util;

import java.util.Scanner;

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
}
