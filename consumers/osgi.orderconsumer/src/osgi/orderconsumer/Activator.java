package osgi.orderconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import osgi.orderproducer.OrderProducer;

public class Activator implements BundleActivator {

    private ServiceReference<OrderProducer> serviceReference;
    private OrderProducer orderProducer;
    private ConsumerUI consumerUI;

    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Order Consumer Started");

        // Retrieve reference to OrderProducer service
        serviceReference = bundleContext.getServiceReference(OrderProducer.class);

        if (serviceReference != null) {
            orderProducer = bundleContext.getService(serviceReference);

            if (orderProducer != null) {
                // Create and show UI
                consumerUI = new ConsumerUI(orderProducer);
                consumerUI.show();
            } else {
                System.out.println("Failed to retrieve OrderProducer service.");
            }
        } else {
            System.out.println("OrderProducer service not found.");
        }
    }

    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Order Consumer Stopped");
        
        // Dispose the UI if it exists
        if (consumerUI != null) {
            consumerUI.dispose();
            consumerUI = null;
        }
        
        if (serviceReference != null) {
            bundleContext.ungetService(serviceReference);
        }
    }
}