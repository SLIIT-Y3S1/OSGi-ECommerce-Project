package osgi.shippingproducer;

import java.security.Provider.Service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	ServiceRegistration serviceRegistration;

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("ShippingManager Publisher Stated");
		serviceRegistration = bundleContext.registerService(ShippingProducer.class.getName(), new ShippingProducerImpl(), null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("ShippingManager Publisher Stoped....");
		serviceRegistration.unregister();
	}

}
