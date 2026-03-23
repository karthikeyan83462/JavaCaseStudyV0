package service;

import model.User;
import storage.StorageManager;
import util.DateUtil;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    public static boolean addEmployee(String name, String email, String phone, String department,
                                     String designation, String skills,String crea, String supervisorId) {
        String empId = DateUtil.generateId("EMP");
        String joiningDate = DateUtil.getCurrentDate();
        String username = email.split("@")[0];
        
        User newUser = new User(empId, username, "Pass@123", "EMPLOYEE", name, email, phone,
                               department, designation, skills, crea, supervisorId, joiningDate);
        StorageManager.writeToFile(StorageManager.getFilePath("users"), newUser.toString());
        return true;
    }

    public static List<User> getAllEmployees() {
        List<User> employees = new ArrayList<>();
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("users"));
        
        for (String line : lines) {
            User user = User.fromString(line);
            if (user != null && user.getRole().equals("EMPLOYEE")) {
                employees.add(user);
            }
        }
        return employees;
    }

    public static User getEmployeeById(String empId) {
        List<User> employees = getAllEmployees();
        for (User emp : employees) {
            if (emp.getUserId().equals(empId)) {
                return emp;
            }
        }
        return null;
    }

    public static boolean updateEmployee(String empId, User updated) {
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("users"));
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        
        for (String line : lines) {
            User user = User.fromString(line);
            if (user != null && user.getUserId().equals(empId)) {
                updatedLines.add(updated.toString());
                found = true;
            } else {
                updatedLines.add(line);
            }
        }
        
        if (found) {
            StorageManager.overwriteFile(StorageManager.getFilePath("users"), updatedLines);
        }
        return found;
    }

    public static boolean deleteEmployee(String empId, String Crea) {
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("users"));
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        
        for (String line : lines) {
            User user = User.fromString(line);
            if (user != null && user.getUserId().equals(empId) && !user.getCreatedBy().equals("Default") && user.getCreatedBy().equals(Crea) && !empId.equals(Crea)) {
                found = true;
            } else {
                updatedLines.add(line);
            }
        }
        
        if (found) {
            StorageManager.overwriteFile(StorageManager.getFilePath("users"), updatedLines);
        }
        return found;
    }

    public static List<User> searchBySkill(String skill) {
        List<User> result = new ArrayList<>();
        List<User> employees = getAllEmployees();

        for (User emp : employees) {
            if (emp.getSkills().toLowerCase().contains(skill.toLowerCase())) {
                result.add(emp);
            }
        }
        return result;
    }

    public static List<User> getTeamMembers(String supervisorId) {
        List<User> team = new ArrayList<>();
        List<User> employees = getAllEmployees();
        
        for (User emp : employees) {
            if (emp.getSupervisorId().equals(supervisorId)) {
                team.add(emp);
            }
        }
        return team;
    }

    public static User getSupervisor(String supervisorId) {
        return getEmployeeById(supervisorId);
    }
}
