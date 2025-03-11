package osgi.reviewproducer;

import java.util.ArrayList;
import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private final List<String> reviews = new ArrayList<>();

    @Override
    public void addReview(String productId, String customerId, String review) {
        String newReview = String.format("| %-15s | %-12s | %-50s |", productId, customerId, review);
        reviews.add(newReview);

        // Print header for the table
        System.out.println("+-----------------+--------------+--------------------------------------------------+");
        System.out.println("| Product ID      | Customer ID  | Review                                          |");
        System.out.println("+-----------------+--------------+--------------------------------------------------+");

        // Print the new review as a table row
        System.out.println(newReview);
        System.out.println("+-----------------+--------------+--------------------------------------------------+");
    }

}