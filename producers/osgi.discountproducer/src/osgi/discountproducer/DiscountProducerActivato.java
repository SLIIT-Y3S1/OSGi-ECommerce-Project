package osgi.discountproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class DiscountProducerActivato implements BundleActivator {
	 private ServiceRegistration<?> registration;
	

	public void start(BundleContext context) throws Exception {
		 System.out.println("Discount Producer Started");
		 
	        DiscountServiceProducer discountService = new DiscountServiceProducerImpl();
	        
	        registration = context.registerService(DiscountServiceProducer.class.getName(), discountService, null);
	        System.out.println("DiscountService Registered!");
	        
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("Discount Producer Stopped");
        registration.unregister();
	}

}
