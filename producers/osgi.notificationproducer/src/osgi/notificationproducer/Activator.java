package osgi.notificationproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceRegistration registration;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("Notification Producer Started");
        NotificationService notificationService = new NotificationServiceImpl();
        registration = context.registerService(NotificationService.class.getName(), notificationService, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("Notification Producer Stopped");
        registration.unregister();
	}
}

class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendNotification(String message) {
        System.out.println("Notification: " + message);
    }
    
    @Override
    public void sendCustomNotification(String username, String action) {
        String message = "User " + username + " performed action: " + action;
        System.out.println("Notification: " + message);
    }
    
    @Override
    public void sendRoleSpecificNotification(String message, String role) {
    	System.out.println();
        if ("Admin".equals(role)) {
            System.out.println("Critical Alert for Admin: " + message);
        } else if ("User".equals(role)) {
            System.out.println("Regular Notification for User: " + message);
        } else {
            System.out.println("Limited Notification for Guest: " + message);
        }
    }
}
