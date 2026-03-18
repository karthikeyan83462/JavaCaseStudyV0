package ui;

import model.User;
import service.AuthService;
import util.ConsoleUtil;

public class MainMenu {

    public static void start() {
        while (true) {
            User user = AuthService.getCurrentUser();
            
            if (user == null) {
                showLoginMenu();
            } else {
                showMainMenu(user);
            }
        }
    }

    private static void showLoginMenu() {
        ConsoleUtil.printHeader("EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println();
        
        int choice = ConsoleUtil.inputInt("Enter your choice: ");
        
        switch (choice) {
            case 1:
                AuthUI.login();
                break;
            case 2:
                AuthUI.register();
                break;
            case 3:
                System.out.println("Thank you for using EMS. Goodbye!");
                System.exit(0);
                break;
            default:
                ConsoleUtil.printError("Invalid choice!");
                ConsoleUtil.pause();
        }
    }

    private static void showMainMenu(User user) {
        String role = user.getRole();
        boolean isManagerOrAdmin = role.equals("ADMIN") || role.equals("MANAGER");
        
        ConsoleUtil.printHeader("EMPLOYEE MANAGEMENT SYSTEM - " + role);
        
        // Build menu based on role
        if (isManagerOrAdmin) {
            System.out.println("1. Employee Management");
            System.out.println("2. Project Management");
            System.out.println("3. Attendance");
            System.out.println("4. Leave Management");
            System.out.println("5. Performance Review");
            System.out.println("6. Reports");
            System.out.println("7. My Profile");
            System.out.println("8. Logout");
            System.out.println();
            
            int choice = ConsoleUtil.inputInt("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    EmployeeUI.show(user);
                    break;
                case 2:
                    ProjectUI.show(user);
                    break;
                case 3:
                    AttendanceUI.show(user);
                    break;
                case 4:
                    LeaveUI.show(user);
                    break;
                case 5:
                    PerformanceUI.show(user);
                    break;
                case 6:
                    ReportUI.show(user);
                    break;
                case 7:
                    ProfileUI.show(user);
                    break;
                case 8:
                    AuthService.logout();
                    ConsoleUtil.printSuccess("Logged out successfully!");
                    ConsoleUtil.pause();
                    break;
                default:
                    ConsoleUtil.printError("Invalid choice!");
                    ConsoleUtil.pause();
            }
        } else {
            // EMPLOYEE menu
            System.out.println("1. Attendance");
            System.out.println("2. Leave Management");
            System.out.println("3. Reports");
            System.out.println("4. My Profile");
            System.out.println("5. Logout");
            System.out.println();
            
            int choice = ConsoleUtil.inputInt("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    AttendanceUI.show(user);
                    break;
                case 2:
                    LeaveUI.show(user);
                    break;
                case 3:
                    ReportUI.show(user);
                    break;
                case 4:
                    ProfileUI.show(user);
                    break;
                case 5:
                    AuthService.logout();
                    ConsoleUtil.printSuccess("Logged out successfully!");
                    ConsoleUtil.pause();
                    break;
                default:
                    ConsoleUtil.printError("Invalid choice!");
                    ConsoleUtil.pause();
            }
        }
    }
}
