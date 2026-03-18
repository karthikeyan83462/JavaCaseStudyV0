package service;

import model.Leave;
import storage.StorageManager;
import util.DateUtil;
import java.util.ArrayList;
import java.util.List;

public class LeaveService {

    public static boolean applyLeave(String empId, String startDate, String endDate, String reason) {
        String leaveId = DateUtil.generateId("LEV");
        Leave leave = new Leave(leaveId, empId, startDate, endDate, reason, "PENDING", "");
        StorageManager.writeToFile(StorageManager.getFilePath("leaves"), leave.toString());
        return true;
    }

    public static List<Leave> getEmployeeLeaves(String empId) {
        List<Leave> result = new ArrayList<>();
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("leaves"));
        
        for (String line : lines) {
            Leave leave = Leave.fromString(line);
            if (leave != null && leave.getEmpId().equals(empId)) {
                result.add(leave);
            }
        }
        return result;
    }

    public static List<Leave> getPendingLeaves() {
        List<Leave> result = new ArrayList<>();
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("leaves"));
        
        for (String line : lines) {
            Leave leave = Leave.fromString(line);
            if (leave != null && leave.getStatus().equals("PENDING")) {
                result.add(leave);
            }
        }
        return result;
    }

    public static Leave getLeaveById(String leaveId) {
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("leaves"));
        
        for (String line : lines) {
            Leave leave = Leave.fromString(line);
            if (leave != null && leave.getLeaveId().equals(leaveId)) {
                return leave;
            }
        }
        return null;
    }

    public static boolean approveLeave(String leaveId, String approvedBy) {
        Leave leave = getLeaveById(leaveId);
        if (leave != null) {
            leave.setStatus("APPROVED");
            leave.setApprovedBy(approvedBy);
            updateLeave(leave);
            return true;
        }
        return false;
    }

    public static boolean rejectLeave(String leaveId, String approvedBy) {
        Leave leave = getLeaveById(leaveId);
        if (leave != null) {
            leave.setStatus("REJECTED");
            leave.setApprovedBy(approvedBy);
            updateLeave(leave);
            return true;
        }
        return false;
    }

    private static boolean updateLeave(Leave updated) {
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("leaves"));
        List<String> newLines = new ArrayList<>();
        boolean found = false;
        
        for (String line : lines) {
            Leave leave = Leave.fromString(line);
            if (leave != null && leave.getLeaveId().equals(updated.getLeaveId())) {
                newLines.add(updated.toString());
                found = true;
            } else {
                newLines.add(line);
            }
        }
        
        if (found) {
            StorageManager.overwriteFile(StorageManager.getFilePath("leaves"), newLines);
        }
        return found;
    }
}
