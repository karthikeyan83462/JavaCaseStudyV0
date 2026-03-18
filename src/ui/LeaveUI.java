package ui;

import model.Leave;
import model.User;
import service.LeaveService;
import util.ConsoleUtil;
import java.util.List;

public class LeaveUI {

    public static void show(User user) {
        while (true) {
            ConsoleUtil.printHeader("LEAVE MANAGEMENT");
            System.out.println("1. Apply for Leave");
            System.out.println("2. View My Leave Requests");
            
            if (user.getRole().equals("MANAGER") || user.getRole().equals("ADMIN")) {
                System.out.println("3. View Pending Leave Requests");
                System.out.println("4. Approve/Reject Leave");
                System.out.println("5. Back");
            } else {
                System.out.println("3. Back");
            }
            System.out.println();

            int choice = ConsoleUtil.inputInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    applyLeave(user);
                    break;
                case 2:
                    viewMyLeaves(user.getUserId());
                    break;
                case 3:
                    if (user.getRole().equals("MANAGER") || user.getRole().equals("ADMIN")) {
                        viewPendingLeaves();
                    } else {
                        return;
                    }
                    break;
                case 4:
                    if (user.getRole().equals("MANAGER") || user.getRole().equals("ADMIN")) {
                        approveRejectLeave(user);
                    }
                    break;
                case 5:
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

    private static void applyLeave(User user) {
        ConsoleUtil.printHeader("APPLY FOR LEAVE");
        String startDate = ConsoleUtil.input("Enter start date (dd-MM-yyyy): ");
        String endDate = ConsoleUtil.input("Enter end date (dd-MM-yyyy): ");
        String reason = ConsoleUtil.input("Enter reason for leave: ");

        if (LeaveService.applyLeave(user.getUserId(), startDate, endDate, reason)) {
            ConsoleUtil.printSuccess("Leave application submitted successfully!");
        } else {
            ConsoleUtil.printError("Failed to apply for leave!");
        }
        ConsoleUtil.pause();
    }

    private static void viewMyLeaves(String empId) {
        ConsoleUtil.printHeader("MY LEAVE REQUESTS");
        List<Leave> leaves = LeaveService.getEmployeeLeaves(empId);

        if (leaves.isEmpty()) {
            ConsoleUtil.printInfo("No leave requests found!");
        } else {
            ConsoleUtil.printLine("ID | From | To | Reason | Status | Approved By");
            ConsoleUtil.printDivider();
            for (Leave leave : leaves) {
                System.out.println(leave.getLeaveId() + " | " + leave.getStartDate() + " | " + leave.getEndDate() +
                                 " | " + leave.getReason() + " | " + leave.getStatus() + " | " + leave.getApprovedBy());
            }
        }
        ConsoleUtil.pause();
    }

    private static void viewPendingLeaves() {
        ConsoleUtil.printHeader("PENDING LEAVE REQUESTS");
        List<Leave> leaves = LeaveService.getPendingLeaves();

        if (leaves.isEmpty()) {
            ConsoleUtil.printInfo("No pending leave requests!");
        } else {
            ConsoleUtil.printLine("ID | Employee | From | To | Reason");
            ConsoleUtil.printDivider();
            for (Leave leave : leaves) {
                System.out.println(leave.getLeaveId() + " | " + leave.getEmpId() + " | " + leave.getStartDate() +
                                 " | " + leave.getEndDate() + " | " + leave.getReason());
            }
        }
        ConsoleUtil.pause();
    }

    private static void approveRejectLeave(User user) {
        ConsoleUtil.printHeader("APPROVE/REJECT LEAVE REQUEST");
        String leaveId = ConsoleUtil.input("Enter leave ID: ");
        
        Leave leave = LeaveService.getLeaveById(leaveId);
        if (leave == null) {
            ConsoleUtil.printError("Leave request not found!");
            ConsoleUtil.pause();
            return;
        }

        System.out.println("\n--- Leave Details ---");
        System.out.println("Employee: " + leave.getEmpId());
        System.out.println("From: " + leave.getStartDate() + " To: " + leave.getEndDate());
        System.out.println("Reason: " + leave.getReason());
        System.out.println("\n1. Approve");
        System.out.println("2. Reject");

        int choice = ConsoleUtil.inputInt("Enter your choice: ");
        boolean success = false;

        if (choice == 1) {
            success = LeaveService.approveLeave(leaveId, user.getUserId());
            if (success) {
                ConsoleUtil.printSuccess("Leave approved!");
            }
        } else if (choice == 2) {
            success = LeaveService.rejectLeave(leaveId, user.getUserId());
            if (success) {
                ConsoleUtil.printSuccess("Leave rejected!");
            }
        } else {
            ConsoleUtil.printError("Invalid choice!");
        }

        ConsoleUtil.pause();
    }
}
