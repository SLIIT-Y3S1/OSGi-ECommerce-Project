package osgi.reviewconsumer;

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
            reviewService.addReview("PROD123", "Alice", "Great product!");
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
