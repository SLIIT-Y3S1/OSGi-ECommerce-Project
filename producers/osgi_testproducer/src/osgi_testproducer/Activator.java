package osgi_testproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	ServiceRegistration serviceRegistration;
	
	public void start(BundleContext context) throws Exception {
		System.out.println("Test Publisher Stated");
		// save on service registry
				serviceRegistration = context.registerService(TestServiceProducer.class.getName(),new TestServiceProducerImpl(), null);
		
	}

	public void stop(BundleContext context) throws Exception {
			System.out.println("Test Publisher Stoped....");
		
			//remove from service registry
			serviceRegistration.unregister();
	}

}
