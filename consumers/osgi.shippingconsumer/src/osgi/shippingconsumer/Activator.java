package osgi.shippingconsumer;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import osgi.shippingproducer.ShippingProducer;
public class Activator implements BundleActivator {
	
	Scanner scanner = new Scanner(System.in);
	private ServiceReference<ShippingProducer> serviceReference;
	private ShippingProducer service;


	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Shipping Consumer Started");
		serviceReference = bundleContext.getServiceReference(ShippingProducer.class);
		if (serviceReference != null) {
			service = bundleContext.getService(serviceReference);
			if (service != null) {
				service.main();
			} else {
				System.out.println("Shipping Service Not Available.");
			}
		} else {
			System.out.println("Shipping Service Not Found.");
		}
	}


	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Shipping Consumer Stopped");
		bundleContext.ungetService(serviceReference);
		
	}

}
