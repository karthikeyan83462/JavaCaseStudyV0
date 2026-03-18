package model;

import java.util.Arrays;
import java.util.List;

public class Project {
    private String projectId;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String status;
    private String assignedEmployees;
    private String requiredSkills;

    public Project(String projectId, String name, String description, String startDate,
                   String endDate, String status, String assignedEmployees, String requiredSkills) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.assignedEmployees = assignedEmployees;
        this.requiredSkills = requiredSkills;
    }

    public String getProjectId() { return projectId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getStatus() { return status; }
    public String getAssignedEmployees() { return assignedEmployees; }
    public String getRequiredSkills() { return requiredSkills; }

    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }
    public void setAssignedEmployees(String assignedEmployees) { this.assignedEmployees = assignedEmployees; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    @Override
    public String toString() {
        return projectId + "|" + name + "|" + description + "|" + startDate + "|" +
               endDate + "|" + status + "|" + assignedEmployees + "|" + requiredSkills;
    }

    public static Project fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split("\\|");
        if (parts.length >= 8) {
            return new Project(parts[0], parts[1], parts[2], parts[3], parts[4],
                              parts[5], parts[6], parts[7]);
        }
        return null;
    }

    public List<String> getEmployeeList() {
        return assignedEmployees.isEmpty() ? Arrays.asList() : Arrays.asList(assignedEmployees.split(";"));
    }

    public List<String> getSkillsList() {
        return Arrays.asList(requiredSkills.split(";"));
    }
}
