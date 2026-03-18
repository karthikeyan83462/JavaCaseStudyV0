package model;

public class Attendance {
    private String empId;
    private String date;
    private String status;

    public Attendance(String empId, String date, String status) {
        this.empId = empId;
        this.date = date;
        this.status = status;
    }

    public String getEmpId() { return empId; }
    public String getDate() { return date; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return empId + "|" + date + "|" + status;
    }

    public static Attendance fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split("\\|");
        if (parts.length >= 3) {
            return new Attendance(parts[0], parts[1], parts[2]);
        }
        return null;
    }
}
