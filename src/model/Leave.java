package model;

public class Leave {
    private String leaveId;
    private String empId;
    private String startDate;
    private String endDate;
    private String reason;
    private String status;
    private String approvedBy;

    public Leave(String leaveId, String empId, String startDate, String endDate,
                 String reason, String status, String approvedBy) {
        this.leaveId = leaveId;
        this.empId = empId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
        this.approvedBy = approvedBy;
    }

    public String getLeaveId() { return leaveId; }
    public String getEmpId() { return empId; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public String getApprovedBy() { return approvedBy; }

    public void setStatus(String status) { this.status = status; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    @Override
    public String toString() {
        return leaveId + "|" + empId + "|" + startDate + "|" + endDate + "|" +
               reason + "|" + status + "|" + approvedBy;
    }

    public static Leave fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split("\\|", -1);  
        if (parts.length >= 7) {
            return new Leave(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
        }
        return null;
    }
}
