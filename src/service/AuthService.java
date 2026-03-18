package service;

import model.User;
import storage.StorageManager;
import util.DateUtil;
import java.util.List;

public class AuthService {
    private static User currentUser;

    public static boolean register(String username, String password, String role) {
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("users"));
        
        for (String line : lines) {
            User user = User.fromString(line);
            if (user != null && user.getUsername().equals(username)) {
                return false;
            }
        }
        
        String prefix;
        if (role.equals("ADMIN")) {
            prefix = "ADM";
        } else if (role.equals("MANAGER")) {
            prefix = "MGR";
        } else {
            prefix = "EMP";
        }
        String userId = DateUtil.generateId(prefix);
        User newUser = new User(userId, username, password, role);
        StorageManager.writeToFile(StorageManager.getFilePath("users"), newUser.toString());
        return true;
    }

    public static boolean registerWithDetails(String username, String password, String role, 
                                             String name, String email, String phone, 
                                             String department, String designation, String skills, String supervisorId) {
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("users"));
        
        for (String line : lines) {
            User user = User.fromString(line);
            if (user != null && user.getUsername().equals(username)) {
                return false;
            }
        }
        String joiningDate = DateUtil.getCurrentDate();
        
        String prefix;
        if (role.equals("ADMIN")) {
            prefix = "ADM";
        } else if (role.equals("MANAGER")) {
            prefix = "MGR";
        } else {
            prefix = "EMP";
        }
        String userId = DateUtil.generateId(prefix);
        User newUser = new User(userId, username, password, role, name, email, phone, 
                               department, designation, skills, supervisorId, joiningDate);
        StorageManager.writeToFile(StorageManager.getFilePath("users"), newUser.toString());
        return true;
    }

    public static User login(String username, String password) {
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("users"));
        
        for (String line : lines) {
            User user = User.fromString(line);
            if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return user;
            }
        }
        return null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }

    public static boolean isAdmin() {
        return currentUser != null && currentUser.getRole().equals("ADMIN");
    }

    public static boolean isManager() {
        return currentUser != null && (currentUser.getRole().equals("MANAGER") || currentUser.getRole().equals("ADMIN"));
    }

    public static boolean isEmployee() {
        return currentUser != null && currentUser.getRole().equals("EMPLOYEE");
    }
}
