package ui;

import model.PerformanceReview;
import model.User;
import service.PerformanceService;
import service.EmployeeService;
import util.ConsoleUtil;
import java.util.List;

public class PerformanceUI {

    public static void show(User user) {
        if (!user.getRole().equals("MANAGER") && !user.getRole().equals("ADMIN")) {
            ConsoleUtil.printError("Access denied!");
            ConsoleUtil.pause();
            return;
        }

        while (true) {
            ConsoleUtil.printHeader("PERFORMANCE REVIEW");
            System.out.println("1. Add Performance Review");
            System.out.println("2. View My Reviews");
            System.out.println("3. View Employee Reviews");
            System.out.println("4. Back");
            System.out.println();

            int choice = ConsoleUtil.inputInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addReview(user);
                    break;
                case 2:
                    viewMyReviews(user.getUserId());
                    break;
                case 3:
                    viewEmployeeReviews();
                    break;
                case 4:
                    return;
                default:
                    ConsoleUtil.printError("Invalid choice!");
                    ConsoleUtil.pause();
            }
        }
    }

    private static void addReview(User user) {
        ConsoleUtil.printHeader("ADD PERFORMANCE REVIEW");
        String empId = ConsoleUtil.input("Enter employee ID: ");
        
        User emp = EmployeeService.getEmployeeById(empId);
        if (emp == null) {
            ConsoleUtil.printError("Employee not found!");
            ConsoleUtil.pause();
            return;
        }

        System.out.println("\nRating Scale: 1-5");
        String rating = ConsoleUtil.input("Enter rating (1-5): ");
        String feedback = ConsoleUtil.input("Enter feedback: ");

        if (PerformanceService.addReview(empId, user.getUserId(), rating, feedback)) {
            ConsoleUtil.printSuccess("Performance review added successfully!");
        } else {
            ConsoleUtil.printError("Failed to add review!");
        }
        ConsoleUtil.pause();
    }

    private static void viewMyReviews(String empId) {
        ConsoleUtil.printHeader("MY PERFORMANCE REVIEWS");
        List<PerformanceReview> reviews = PerformanceService.getEmployeeReviews(empId);

        if (reviews.isEmpty()) {
            ConsoleUtil.printInfo("No performance reviews found!");
        } else {
            ConsoleUtil.printLine("ID | Rating | Feedback | Date");
            ConsoleUtil.printDivider();
            for (PerformanceReview review : reviews) {
                System.out.println(review.getReviewId() + " | " + review.getRating() + "/5 | " +
                                 review.getFeedback() + " | " + review.getReviewDate());
            }
        }
        ConsoleUtil.pause();
    }

    private static void viewEmployeeReviews() {
        ConsoleUtil.printHeader("VIEW EMPLOYEE REVIEWS");
        String empId = ConsoleUtil.input("Enter employee ID: ");
        List<PerformanceReview> reviews = PerformanceService.getEmployeeReviews(empId);

        if (reviews.isEmpty()) {
            ConsoleUtil.printInfo("No performance reviews found for this employee!");
        } else {
            ConsoleUtil.printLine("Employee: " + empId);
            ConsoleUtil.printLine("ID | Rating | Feedback | Reviewer | Date");
            ConsoleUtil.printDivider();
            for (PerformanceReview review : reviews) {
                System.out.println(review.getReviewId() + " | " + review.getRating() + "/5 | " +
                                 review.getFeedback() + " | " + review.getReviewerEmpId() + " | " + review.getReviewDate());
            }
        }
        ConsoleUtil.pause();
    }
}
