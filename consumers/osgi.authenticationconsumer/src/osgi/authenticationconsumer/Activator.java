package osgi.authenticationconsumer;

import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import osgi.authenticationproducer.UserProfileService;


public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceReference serviceReference;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("UserProfile Consumer Started");
        serviceReference = context.getServiceReference(UserProfileService.class.getName());
        UserProfileService userProfileService = (UserProfileService) context.getService(serviceReference);
        
//        register
        System.out.println();
        userProfileService.registerUser("admin1", "admin123", "Admin");
        userProfileService.registerUser("user1", "password1", "User");
        userProfileService.registerUser("guest1", "guest123", "Guest");
        
//        login
        System.out.println();
        userProfileService.loginUser("admin1", "admin123");
        userProfileService.loginUser("user1", "password1");
        userProfileService.loginUser("guest1", "guest123");
        
//        demonstrate role-specific notifications
        System.out.println("\nSending role-specific notifications:");
        userProfileService.changeUserRole("admin1", "user1", "Admin");
        userProfileService.changeUserRole("guest1", "guest", "User");
        
//        list all users and their roles
        System.out.println("\nListing all users and their roles:");
        Map<String, String> allUsers = userProfileService.listAllUsers("admin1");
        if(allUsers.isEmpty())
        	System.out.println("No user found");
        else {
        	for (Map.Entry<String, String> entry : allUsers.entrySet()) {
                System.out.println("User: " + entry.getKey() + ", Role: " + entry.getValue());
            }
        }
        
//        logout
        System.out.println();
        userProfileService.logoutUser("admin1");
        userProfileService.logoutUser("user1");
        userProfileService.logoutUser("guest1");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("UserProfile Consumer Stopped");
        context.ungetService(serviceReference);
        Activator.context = null;
	}
}
