package model;

public class PerformanceReview {
    private String reviewId;
    private String empId;
    private String reviewerEmpId;
    private String rating;
    private String feedback;
    private String reviewDate;

    public PerformanceReview(String reviewId, String empId, String reviewerEmpId,
                            String rating, String feedback, String reviewDate) {
        this.reviewId = reviewId;
        this.empId = empId;
        this.reviewerEmpId = reviewerEmpId;
        this.rating = rating;
        this.feedback = feedback;
        this.reviewDate = reviewDate;
    }

    public String getReviewId() { return reviewId; }
    public String getEmpId() { return empId; }
    public String getReviewerEmpId() { return reviewerEmpId; }
    public String getRating() { return rating; }
    public String getFeedback() { return feedback; }
    public String getReviewDate() { return reviewDate; }

    @Override
    public String toString() {
        return reviewId + "|" + empId + "|" + reviewerEmpId + "|" + rating + "|" +
               feedback + "|" + reviewDate;
    }

    public static PerformanceReview fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split("\\|");
        if (parts.length >= 6) {
            return new PerformanceReview(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        }
        return null;
    }
}
