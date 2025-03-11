package osgi.reviewproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class ReviewProducerActivator implements BundleActivator {
    private ServiceRegistration<ReviewService> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        // Create an instance of ReviewServiceImpl
        ReviewServiceImpl reviewService = new ReviewServiceImpl();
        
        // Register the ReviewService implementation as a service
        registration = context.registerService(ReviewService.class, reviewService, null);
        System.out.println("ReviewProducer bundle started and ReviewService registered.");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        // Unregister the service when the bundle stops
        if (registration != null) {
            registration.unregister();
            System.out.println("ReviewProducer bundle stopped and ReviewService unregistered.");
        }
    }
}
