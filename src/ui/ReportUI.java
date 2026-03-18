package ui;

import model.User;
import model.Attendance;
import model.Leave;
import model.Project;
import service.EmployeeService;
import service.AttendanceService;
import service.LeaveService;
import service.ProjectService;
import util.ConsoleUtil;
import java.util.List;

public class ReportUI {

    public static void show(User user) {
        // All roles can access reports (employees see their own, managers/admins see all)
        if (!user.getRole().equals("ADMIN") && !user.getRole().equals("MANAGER") && !user.getRole().equals("EMPLOYEE")) {
            ConsoleUtil.printError("Access denied!");
            ConsoleUtil.pause();
            return;
        }

        while (true) {
            ConsoleUtil.printHeader("REPORTS");
            System.out.println("1. Employee Attendance Report");
            System.out.println("2. Leave Report");
            System.out.println("3. Project Allocation Report");
            System.out.println("4. Employee Summary Report");
            System.out.println("5. Back");
            System.out.println();

            int choice = ConsoleUtil.inputInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    attendanceReport();
                    break;
                case 2:
                    leaveReport();
                    break;
                case 3:
                    projectReport();
                    break;
                case 4:
                    employeeSummaryReport();
                    break;
                case 5:
                    return;
                default:
                    ConsoleUtil.printError("Invalid choice!");
                    ConsoleUtil.pause();
            }
        }
    }

    private static void attendanceReport() {
        ConsoleUtil.printHeader("ATTENDANCE REPORT");
        List<Attendance> allAttendance = AttendanceService.getAllAttendance();
        List<User> employees = EmployeeService.getAllEmployees();

        if (allAttendance.isEmpty()) {
            ConsoleUtil.printInfo("No attendance records found!");
        } else {
            ConsoleUtil.printLine("Employee | Department | Present Days | Absent Days");
            ConsoleUtil.printDivider();

            for (User emp : employees) {
                int present = 0;
                int absent = 0;

                for (Attendance att : allAttendance) {
                    if (att.getEmpId().equals(emp.getUserId())) {
                        if (att.getStatus().equals("PRESENT")) {
                            present++;
                        } else if (att.getStatus().equals("ABSENT")) {
                            absent++;
                        }
                    }
                }

                if (present > 0 || absent > 0) {
                    System.out.println(emp.getName() + " | " + emp.getDepartment() + " | " + present + " | " + absent);
                }
            }
        }
        ConsoleUtil.pause();
    }

    private static void leaveReport() {
        ConsoleUtil.printHeader("LEAVE REPORT");
        List<Leave> allLeaves = LeaveService.getPendingLeaves();

        if (allLeaves.isEmpty()) {
            ConsoleUtil.printInfo("No pending leave requests!");
        } else {
            ConsoleUtil.printLine("Employee | From | To | Reason | Status");
            ConsoleUtil.printDivider();

            for (Leave leave : allLeaves) {
                User emp = EmployeeService.getEmployeeById(leave.getEmpId());
                if (emp != null) {
                    System.out.println(emp.getName() + " | " + leave.getStartDate() + " | " +
                                     leave.getEndDate() + " | " + leave.getReason() + " | " + leave.getStatus());
                }
            }
        }
        ConsoleUtil.pause();
    }

    private static void projectReport() {
        ConsoleUtil.printHeader("PROJECT ALLOCATION REPORT");
        List<Project> projects = ProjectService.getAllProjects();

        if (projects.isEmpty()) {
            ConsoleUtil.printInfo("No projects found!");
        } else {
            ConsoleUtil.printLine("Project | Status | Assigned Employees | Required Skills");
            ConsoleUtil.printDivider();

            for (Project proj : projects) {
                String empCount = proj.getAssignedEmployees().isEmpty() ? "0" : String.valueOf(proj.getEmployeeList().size());
                System.out.println(proj.getName() + " | " + proj.getStatus() + " | " + empCount + " | " + proj.getRequiredSkills());
            }
        }
        ConsoleUtil.pause();
    }

    private static void employeeSummaryReport() {
        ConsoleUtil.printHeader("EMPLOYEE SUMMARY REPORT");
        List<User> employees = EmployeeService.getAllEmployees();

        if (employees.isEmpty()) {
            ConsoleUtil.printInfo("No employees found!");
        } else {
            ConsoleUtil.printLine("Name | Department | Designation | Skills | Projects");
            ConsoleUtil.printDivider();

            for (User emp : employees) {
                List<Project> projects = ProjectService.getEmployeeProjects(emp.getUserId());
                System.out.println(emp.getName() + " | " + emp.getDepartment() + " | " + emp.getDesignation() +
                                 " | " + emp.getSkills() + " | " + projects.size());
            }
        }
        ConsoleUtil.pause();
    }
}
