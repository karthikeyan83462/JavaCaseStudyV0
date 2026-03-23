package ui;

import model.Project;
import model.User;
import service.ProjectService;
import service.EmployeeService;
import util.ConsoleUtil;
import util.DateUtil;
import java.util.List;

public class ProjectUI {

    public static void show(User user) {
        if (!user.getRole().equals("MANAGER") && !user.getRole().equals("ADMIN")) {
            ConsoleUtil.printError("Access denied!");
            ConsoleUtil.pause();
            return;
        }

        while (true) {
            ConsoleUtil.printHeader("PROJECT MANAGEMENT");
            System.out.println("1. Create New Project");
            System.out.println("2. View All Projects");
            System.out.println("3. View Project Details");
            System.out.println("4. Assign Employee to Project");
            System.out.println("5. Update Project Status");
            System.out.println("6. Resource Availability");
            System.out.println("7. Back");
            System.out.println();

            int choice = ConsoleUtil.inputInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    createProject();
                    break;
                case 2:
                    viewAllProjects();
                    break;
                case 3:
                    viewProjectDetails();
                    break;
                case 4:
                    assignEmployee();
                    break;
                case 5:
                    updateStatus();
                    break;
                case 6:
                    viewAvailability();
                    break;
                case 7:
                    return;
                default:
                    ConsoleUtil.printError("Invalid choice!");
                    ConsoleUtil.pause();
            }
        }
    }

    private static void createProject() {
        ConsoleUtil.printHeader("CREATE NEW PROJECT");
        String name = ConsoleUtil.inputRequired("Enter project name: ");
        if (name.length() < 3) {
                ConsoleUtil.printError("Name must be at least 3 characters!");
                ConsoleUtil.pause();
                return;
            }

        String description = ConsoleUtil.inputRequired("Enter description: ");
        if (description.length() < 5) {
            ConsoleUtil.printError("Description must be at least 5 characters!");
            ConsoleUtil.pause();
            return;
        }

        String startDate = ConsoleUtil.inputRequired("Enter start date (dd-MM-yyyy): ");
        if (!DateUtil.isValidDate(startDate)) {
            ConsoleUtil.printError("Invalid start date!");
            ConsoleUtil.pause();
            return;
        }

        String endDate = ConsoleUtil.inputRequired("Enter end date (dd-MM-yyyy): ");

        if (!DateUtil.isValidDate(endDate)) {
            ConsoleUtil.printError("Invalid end date!");
            ConsoleUtil.pause();
            return;
        }
       
        String skills = ConsoleUtil.inputRequired("Enter required skills (semicolon separated): ");
        if (!skills.contains(";")) {
            ConsoleUtil.printError("Please enter skills separated by semicolon (e.g. Java;SQL)!");
            ConsoleUtil.pause();
            return;
        }

        if (ProjectService.createProject(name, description, startDate, endDate, skills)) {
            ConsoleUtil.printSuccess("Project created successfully!");
        } else {
            ConsoleUtil.printError("Failed to create project!");
        }
        ConsoleUtil.pause();
    }

    private static void viewAllProjects() {
        ConsoleUtil.printHeader("ALL PROJECTS");
        List<Project> projects = ProjectService.getAllProjects();

        if (projects.isEmpty()) {
            ConsoleUtil.printInfo("No projects found!");
        } else {
            ConsoleUtil.printLine("ID | Name | Status | Start Date | End Date");
            ConsoleUtil.printDivider();
            for (Project proj : projects) {
                System.out.println(proj.getProjectId() + " | " + proj.getName() + " | " + proj.getStatus() + " | " +
                                 proj.getStartDate() + " | " + proj.getEndDate());
            }
        }
        ConsoleUtil.pause();
    }

    private static void viewProjectDetails() {
        ConsoleUtil.printHeader("VIEW PROJECT DETAILS");
        String projectId = ConsoleUtil.inputRequired("Enter project ID: ");
        Project proj = ProjectService.getProjectById(projectId);

        if (proj == null) {
            ConsoleUtil.printError("Project not found!");
        } else {
            ConsoleUtil.printLine("\n--- Project Details ---");
            ConsoleUtil.printLine("ID: " + proj.getProjectId());
            ConsoleUtil.printLine("Name: " + proj.getName());
            ConsoleUtil.printLine("Description: " + proj.getDescription());
            ConsoleUtil.printLine("Start Date: " + proj.getStartDate());
            ConsoleUtil.printLine("End Date: " + proj.getEndDate());
            ConsoleUtil.printLine("Status: " + proj.getStatus());
            ConsoleUtil.printLine("Required Skills: " + proj.getRequiredSkills());
            ConsoleUtil.printLine("Assigned Employees: " + (proj.getAssignedEmployees().isEmpty() ? "None" : proj.getAssignedEmployees()));
        }
        ConsoleUtil.pause();
    }

    private static void assignEmployee() {
        ConsoleUtil.printHeader("ASSIGN EMPLOYEE TO PROJECT");
        String projectId = ConsoleUtil.input("Enter project ID: ");
        String empId = ConsoleUtil.input("Enter employee ID: ");

        Project proj = ProjectService.getProjectById(projectId);
        User emp = EmployeeService.getEmployeeById(empId);

        if (proj == null) {
            ConsoleUtil.printError("Project not found!");
        } else if (emp == null) {
            ConsoleUtil.printError("Employee not found!");
        } else if (ProjectService.assignEmployeeToProject(projectId, empId)) {
            ConsoleUtil.printSuccess("Employee assigned to project successfully!");
        } else {
            ConsoleUtil.printError("Employee already assigned or assignment failed!");
        }
        ConsoleUtil.pause();
    }

    private static void updateStatus() {
        ConsoleUtil.printHeader("UPDATE PROJECT STATUS");
        String projectId = ConsoleUtil.input("Enter project ID: ");

        System.out.println("\nSelect Status:");
        System.out.println("1. ONGOING");
        System.out.println("2. COMPLETED");
        System.out.println("3. ON_HOLD");

        int statusChoice = ConsoleUtil.inputInt("Enter choice: ");
        String status = "";

        switch (statusChoice) {
            case 1:
                status = "ONGOING";
                break;
            case 2:
                status = "COMPLETED";
                break;
            case 3:
                status = "ON_HOLD";
                break;
            default:
                ConsoleUtil.printError("Invalid status!");
                ConsoleUtil.pause();
                return;
        }

        if (ProjectService.updateProjectStatus(projectId, status)) {
            ConsoleUtil.printSuccess("Project status updated successfully!");
        } else {
            ConsoleUtil.printError("Failed to update project status!");
        }
        ConsoleUtil.pause();
    }

    private static void viewAvailability() {
        ConsoleUtil.printHeader("RESOURCE AVAILABILITY DASHBOARD");
        List<User> allEmployees = EmployeeService.getAllEmployees();

        if (allEmployees.isEmpty()) {
            ConsoleUtil.printInfo("No employees found!");
        } else {
            ConsoleUtil.printLine("Available Resources:");
            ConsoleUtil.printDivider();
            for (User emp : allEmployees) {
                List<Project> projects = ProjectService.getEmployeeProjects(emp.getUserId());
                String availability = projects.size() < 3 ? "AVAILABLE" : "BUSY";
                System.out.println(emp.getName() + " (" + emp.getUserId() + ") - " + availability +
                                 " [Projects: " + projects.size() + "]");
            }
        }
        ConsoleUtil.pause();
    }
}
