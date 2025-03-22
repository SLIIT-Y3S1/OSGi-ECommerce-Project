package osgi.paymentproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	 private ServiceRegistration<?> registration;
	    
	    @Override
	    public void start(BundleContext context) throws Exception {
	        // Create the service implementation
	        PaymentProducer paymentProducer = new PaymentProducerImpl();
	        
	        // Register the service with the OSGi service registry
	        registration = context.registerService(
	            PaymentProducer.class.getName(), paymentProducer, null);
	        
	        System.out.println("Payment Producer service registered.");
	    }
	    
	    @Override
	    public void stop(BundleContext context) throws Exception {
	        // Unregister our service
	        if (registration != null) {
	            registration.unregister();
	            registration = null;
	        }
	        
	        System.out.println("Payment Producer service unregistered.");
	    }

}
