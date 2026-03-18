package service;

import model.Project;
import storage.StorageManager;
import util.DateUtil;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    public static boolean createProject(String name, String description, String startDate,
                                        String endDate, String requiredSkills) {
        String projectId = DateUtil.generateId("PROJ");
        Project project = new Project(projectId, name, description, startDate, endDate, "ONGOING", "", requiredSkills);
        StorageManager.writeToFile(StorageManager.getFilePath("projects"), project.toString());
        return true;
    }

    public static List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("projects"));
        
        for (String line : lines) {
            Project proj = Project.fromString(line);
            if (proj != null) {
                projects.add(proj);
            }
        }
        return projects;
    }

    public static Project getProjectById(String projectId) {
        List<Project> projects = getAllProjects();
        for (Project proj : projects) {
            if (proj.getProjectId().equals(projectId)) {
                return proj;
            }
        }
        return null;
    }

    public static boolean assignEmployeeToProject(String projectId, String empId) {
        Project project = getProjectById(projectId);
        if (project == null) return false;
        
        List<String> empIds = project.getEmployeeList();
        if (!empIds.contains(empId)) {
            String newAssigned = project.getAssignedEmployees().isEmpty() ? empId :
                               project.getAssignedEmployees() + ";" + empId;
            project.setAssignedEmployees(newAssigned);
            updateProject(project);
            return true;
        }
        return false;
    }

    public static List<Project> getEmployeeProjects(String empId) {
        List<Project> result = new ArrayList<>();
        List<Project> projects = getAllProjects();
        
        for (Project proj : projects) {
            if (proj.getAssignedEmployees().contains(empId)) {
                result.add(proj);
            }
        }
        return result;
    }

    public static boolean updateProjectStatus(String projectId, String status) {
        Project project = getProjectById(projectId);
        if (project != null) {
            project.setStatus(status);
            updateProject(project);
            return true;
        }
        return false;
    }

    public static boolean updateProject(Project updated) {
        List<Project> projects = getAllProjects();
        List<String> lines = new ArrayList<>();
        boolean found = false;
        
        for (Project proj : projects) {
            if (proj.getProjectId().equals(updated.getProjectId())) {
                lines.add(updated.toString());
                found = true;
            } else {
                lines.add(proj.toString());
            }
        }
        
        if (found) {
            StorageManager.overwriteFile(StorageManager.getFilePath("projects"), lines);
        }
        return found;
    }

    public static boolean removeEmployeeFromProject(String projectId, String empId) {
        Project project = getProjectById(projectId);
        if (project == null) return false;
        
        String current = project.getAssignedEmployees();
        String[] empIds = current.split(";");
        List<String> updated = new ArrayList<>();
        
        for (String id : empIds) {
            if (!id.equals(empId)) {
                updated.add(id);
            }
        }
        
        project.setAssignedEmployees(String.join(";", updated));
        updateProject(project);
        return true;
    }
}
