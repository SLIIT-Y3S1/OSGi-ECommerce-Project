package osgi.notificationconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import osgi.notificationproducer.NotificationService;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceReference serviceReference;


	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		System.out.println("Notification Consumer Started");
        serviceReference = context.getServiceReference(NotificationService.class.getName());
        NotificationService notificationService = (NotificationService) context.getService(serviceReference);
        notificationService.sendNotification("Hello from Notification Consumer");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Notification Consumer Stopped");
        context.ungetService(serviceReference);
        Activator.context = null;
	}
}
