package osgi.inventoryproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	ServiceRegistration serviceRegistration;

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("InventryManager Publisher Started");
		// save on service registry
		serviceRegistration = bundleContext.registerService(InventoryProducer.class.getName(), new InventoryProducerImpl(), null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("InventryManager Publisher Stoped....");

		// remove from service registry
		serviceRegistration.unregister();
	}

}
