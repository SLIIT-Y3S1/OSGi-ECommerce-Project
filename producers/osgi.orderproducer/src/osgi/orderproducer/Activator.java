package osgi.orderproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private ServiceRegistration<OrderProducer> serviceRegistration;

    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Order Producer Started");
        // Register the service
        serviceRegistration = bundleContext.registerService(
            OrderProducer.class, 
            new OrderProducerImp(), 
            null
        );
    }

    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Order Producer Stopped");
        // Unregister the service
        serviceRegistration.unregister();
    }
}
