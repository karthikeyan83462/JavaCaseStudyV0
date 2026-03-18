package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private String userId;
    private String username;
    private String password;
    private String role;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String designation;
    private String skills;
    private String supervisorId;
    private String joiningDate;

    // Constructor for ADMIN/MANAGER users (basic auth only)
    public User(String userId, String username, String password, String role) {
        this(userId, username, password, role, "", "", "", "", "", "", "", "");
    }

    // Constructor for EMPLOYEE users (with full employee details)
    public User(String userId, String username, String password, String role, String name,
                String email, String phone, String department, String designation,
                String skills, String supervisorId, String joiningDate) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.designation = designation;
        this.skills = skills;
        this.supervisorId = supervisorId;
        this.joiningDate = joiningDate;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDepartment() { return department; }
    public String getDesignation() { return designation; }
    public String getSkills() { return skills; }
    public String getSupervisorId() { return supervisorId; }
    public String getJoiningDate() { return joiningDate; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDepartment(String department) { this.department = department; }
    public void setDesignation(String designation) { this.designation = designation; }
    public void setSkills(String skills) { this.skills = skills; }
    public void setSupervisorId(String supervisorId) { this.supervisorId = supervisorId; }

    public List<String> getSkillsList() {
        return skills != null && !skills.isEmpty() ? Arrays.asList(skills.split(";")) : new ArrayList<>();
    }

    @Override
    public String toString() {
        return userId + "|" + username + "|" + password + "|" + role + "|" + name + "|" +
               email + "|" + phone + "|" + department + "|" + designation + "|" +
               skills + "|" + supervisorId + "|" + joiningDate;
    }

    public static User fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split("\\|", -1);  // -1 to preserve trailing empty strings
        if (parts.length >= 4) {
            if (parts.length >= 12) {
                return new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],
                               parts[6], parts[7], parts[8], parts[9], parts[10], parts[11]);
            } else {
                return new User(parts[0], parts[1], parts[2], parts[3]);
            }
        }
        return null;
    }
}
