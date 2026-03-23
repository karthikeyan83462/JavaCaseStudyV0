package ui;

import model.Attendance;
import model.User;
import service.AttendanceService;
import util.ConsoleUtil;
import java.util.List;

public class AttendanceUI {

    public static void show(User user) {
        while (true) {
            ConsoleUtil.printHeader("ATTENDANCE MANAGEMENT");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View My Attendance");
            
            if (user.getRole().equals("MANAGER") || user.getRole().equals("ADMIN")) {
                System.out.println("3. View Employee Attendance");
            }
            
            System.out.println((user.getRole().equals("MANAGER") || user.getRole().equals("ADMIN")) ? "4. Back" : "3. Back");
            System.out.println();

            int choice = ConsoleUtil.inputInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    markAttendance(user);
                    break;
                case 2:
                    viewAttendance(user.getUserId());
                    break;
                case 3:
                    if (user.getRole().equals("MANAGER") || user.getRole().equals("ADMIN")) {
                        viewEmployeeAttendance();
                    } else {
                        return;
                    }
                    break;
                case 4:
                    if (user.getRole().equals("MANAGER") || user.getRole().equals("ADMIN")) {
                        return;
                    }
                    break;
                default:
                    ConsoleUtil.printError("Invalid choice!");
                    ConsoleUtil.pause();
            }
        }
    }

    private static void markAttendance(User user) {
        ConsoleUtil.printHeader("MARK ATTENDANCE");
        System.out.println("1. Present");
        System.out.println("2. Absent");
        System.out.println("3. Leave");

        int statusChoice = ConsoleUtil.inputInt("Enter status: ");
        String status = "";

        switch (statusChoice) {
            case 1:
                status = "PRESENT";
                break;
            case 2:
                status = "ABSENT";
                break;
            case 3:
                status = "LEAVE";
                break;
            default:
                ConsoleUtil.printError("Invalid status!");
                ConsoleUtil.pause();
                return;
        }

        if (AttendanceService.markAttendance(user.getUserId(), status)) {
            ConsoleUtil.printSuccess("Attendance marked as " + status);
        } else {
            ConsoleUtil.printError("Attendance already marked for today!");
        }
        ConsoleUtil.pause();
    }

    private static void viewAttendance(String empId) {
        ConsoleUtil.printHeader("MY ATTENDANCE");
        List<Attendance> attendance = AttendanceService.getEmployeeAttendance(empId);

        if (attendance.isEmpty()) {
            ConsoleUtil.printInfo("No attendance records found!");
        } else {
            ConsoleUtil.printLine("Date | Status");
            ConsoleUtil.printDivider();
            for (Attendance att : attendance) {
                System.out.println(att.getDate() + " | " + att.getStatus());
            }
            
            int presentCount = AttendanceService.getEmployeeAttendanceCount(empId);
            ConsoleUtil.printLine("\nTotal Present Days: " + presentCount);
        }
        ConsoleUtil.pause();
    }

    private static void viewEmployeeAttendance() {
        ConsoleUtil.printHeader("VIEW EMPLOYEE ATTENDANCE");
        String empId = ConsoleUtil.input("Enter employee ID: ");
        List<Attendance> attendance = AttendanceService.getEmployeeAttendance(empId);

        if (attendance.isEmpty()) {
            ConsoleUtil.printInfo("No attendance records found!");
        } else {
            ConsoleUtil.printLine("Employee: " + empId);
            ConsoleUtil.printLine("Date | Status");
            ConsoleUtil.printDivider();
            for (Attendance att : attendance) {
                System.out.println(att.getDate() + " | " + att.getStatus());
            }
            
            int presentCount = AttendanceService.getEmployeeAttendanceCount(empId);
            ConsoleUtil.printLine("\nTotal Present Days: " + presentCount);
        }
        ConsoleUtil.pause();
    }
}
