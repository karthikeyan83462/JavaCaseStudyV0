package service;

import model.PerformanceReview;
import storage.StorageManager;
import util.DateUtil;
import java.util.ArrayList;
import java.util.List;

public class PerformanceService {

    public static boolean addReview(String empId, String reviewerEmpId, String rating, String feedback) {
        String reviewId = DateUtil.generateId("PERF");
        String reviewDate = DateUtil.getCurrentDate();
        PerformanceReview review = new PerformanceReview(reviewId, empId, reviewerEmpId, rating, feedback, reviewDate);
        StorageManager.writeToFile(StorageManager.getFilePath("performance"), review.toString());
        return true;
    }

    public static List<PerformanceReview> getEmployeeReviews(String empId) {
        List<PerformanceReview> result = new ArrayList<>();
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("performance"));
        
        for (String line : lines) {
            PerformanceReview review = PerformanceReview.fromString(line);
            if (review != null && review.getEmpId().equals(empId)) {
                result.add(review);
            }
        }
        return result;
    }

    public static List<PerformanceReview> getAllReviews() {
        List<PerformanceReview> result = new ArrayList<>();
        List<String> lines = StorageManager.readFile(StorageManager.getFilePath("performance"));
        
        for (String line : lines) {
            PerformanceReview review = PerformanceReview.fromString(line);
            if (review != null) {
                result.add(review);
            }
        }
        return result;
    }
}
