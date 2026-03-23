package service;

import model.Attendance;
import storage.StorageManager;
import util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class AttendanceService {

    public static boolean markAttendance(String empId, String status) {
        String date = DateUtil.getCurrentDate();
        String filePath = StorageManager.getFilePath("attendance");

        // Check if already marked
        if (isAttendanceAlreadyMarked(empId, date)) {
            return false;
        }

        Attendance attendance = new Attendance(empId, date, status);
        StorageManager.writeToFile(filePath, attendance.toString());
        return true;
    }

    private static boolean isAttendanceAlreadyMarked(String empId, String date) {
        List<String> lines = StorageManager.readFile(
                StorageManager.getFilePath("attendance")
        );

        for (String line : lines) {
            Attendance att = Attendance.fromString(line);
            if (att != null &&
                att.getEmpId().equals(empId) &&
                att.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    public static List<Attendance> getEmployeeAttendance(String empId) {
        List<Attendance> result = new ArrayList<>();
        List<String> lines = StorageManager.readFile(
                StorageManager.getFilePath("attendance")
        );

        for (String line : lines) {
            Attendance att = Attendance.fromString(line);
            if (att != null && att.getEmpId().equals(empId)) {
                result.add(att);
            }
        }
        return result;
    }

    public static List<Attendance> getAllAttendance() {
        List<Attendance> result = new ArrayList<>();
        List<String> lines = StorageManager.readFile(
                StorageManager.getFilePath("attendance")
        );

        for (String line : lines) {
            Attendance att = Attendance.fromString(line);
            if (att != null) {
                result.add(att);
            }
        }
        return result;
    }

    public static int getEmployeeAttendanceCount(String empId) {
        List<Attendance> attendance = getEmployeeAttendance(empId);
        int count = 0;

        for (Attendance att : attendance) {
            if ("PRESENT".equals(att.getStatus())) {
                count++;
            }
        }
        return count;
    }
}