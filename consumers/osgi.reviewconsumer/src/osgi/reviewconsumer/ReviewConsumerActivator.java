package osgi.reviewconsumer;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import osgi.reviewproducer.ReviewService;

public class ReviewConsumerActivator implements BundleActivator {
    private ServiceReference<?> reviewServiceReference;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Review Consumer Started");

        reviewServiceReference = context.getServiceReference(ReviewService.class.getName());
        if (reviewServiceReference != null) {
            ReviewService reviewService = (ReviewService) context.getService(reviewServiceReference);
            // get user inputs -----
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Product ID: ");
            String prodctId = scanner.next();
            
            System.out.print("Enter Your Name: ");
            String name = scanner.next();
            
            System.out.print("Enter Your feedback here: ");
            String feedback = scanner.next();
            
            reviewService.addReview(prodctId, name, feedback);
        } else {
            System.out.println("ReviewService not found!");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Review Consumer Stopped");
        context.ungetService(reviewServiceReference);
    }
}
