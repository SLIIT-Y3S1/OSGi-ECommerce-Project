package osgi.authenticationproducer;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import osgi.notificationproducer.NotificationService;


public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceRegistration registration;
    private HashMap<String, String> userMap = new HashMap<>();
    private HashMap<String, String> userRoles = new HashMap<>();
    private ServiceReference<NotificationService> notificationServiceRef;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("UserProfile Producer Started");
		
		notificationServiceRef = context.getServiceReference(NotificationService.class);
        NotificationService notificationService = context.getService(notificationServiceRef);
        UserProfileService userProfileService = new UserProfileServiceImpl(userMap, userRoles, notificationService);
        registration = context.registerService(UserProfileService.class.getName(), userProfileService, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("UserProfile Producer Stopped");
        registration.unregister();
        context.ungetService(notificationServiceRef);
        Activator.context = null;
	}
}

class UserProfileServiceImpl implements UserProfileService {
    private HashMap<String, String> userMap;
    private HashMap<String, String> userRoles;
    private NotificationService notificationService;

    public UserProfileServiceImpl(HashMap<String, String> userMap, HashMap<String, String> userRoles, NotificationService notificationService) {
        this.userMap = userMap;
        this.userRoles = userRoles;
        this.notificationService = notificationService;
    }

    @Override
    public void registerUser(String username, String password, String role) {
    	if (userMap.containsKey(username)) {
    		notificationService.sendNotification("User " + username + " is already exists");
            return;
        }
        userMap.put(username, password);
        userRoles.put(username, role);
        
        notificationService.sendNotification("User " + username + " is registered with role: " + role);
    }

    @Override
    public boolean loginUser(String username, String password) {
        if (userMap.containsKey(username) && userMap.get(username).equals(password)) {
            notificationService.sendCustomNotification(username, "login");
            return true;
        }
        notificationService.sendNotification("User " + username + " login is failed");
        return false;
    }
    
    @Override
    public void logoutUser(String username) {
        if (userMap.containsKey(username)) {
            notificationService.sendCustomNotification(username, "logout");
        } else {
            notificationService.sendNotification("User " + username + " is not found");
        }
    }
    
    @Override
    public String getUserRole(String username) {
        return userRoles.getOrDefault(username, "Guest");
    }
    
    @Override
    public void changeUsername(String requesterUsername, String oldUsername, String newUsername) {
        String requesterRole = userRoles.getOrDefault(requesterUsername, "Guest");

        if (!"User".equals(requesterRole) && !"Admin".equals(requesterRole)) {
            System.out.println("Access Denied: Only User and Admin can change usernames.");
            notificationService.sendRoleSpecificNotification("Access Denied: Only User and Admin can change usernames.", requesterRole);
            return;
        }

        if (userMap.containsKey(oldUsername)) {
            String password = userMap.get(oldUsername);
            String role = userRoles.get(oldUsername);

            userMap.remove(oldUsername);
            userRoles.remove(oldUsername);
            userMap.put(newUsername, password);
            userRoles.put(newUsername, role);

            notificationService.sendNotification("Your username has been changed to " + newUsername);
        } else {
            notificationService.sendNotification("User Not Found: " + oldUsername);
        }
    }
    
    @Override
    public void changeUserRole(String requesterUsername, String targetUsername, String newRole) {
        String requesterRole = userRoles.getOrDefault(requesterUsername, "Guest");

        if (!"Admin".equals(requesterRole)) {
        	notificationService.sendRoleSpecificNotification("Access Denied: Only User and Admin can change roles.", requesterRole);
            return;
        }
        if (userRoles.containsKey(targetUsername)) {
            String oldRole = userRoles.get(targetUsername);
            userRoles.put(targetUsername, newRole);
            
            notificationService.sendRoleSpecificNotification("Your role has been changed to " + newRole, newRole);
        } else {
        	notificationService.sendNotification("User Not Found: " + targetUsername);
        }
    }
    
    @Override
    public Map<String, String> listAllUsers(String requesterUsername) {
    	String requesterRole = userRoles.getOrDefault(requesterUsername, "Guest");
    	if (!"Admin".equals(requesterRole)) {
            System.out.println("Access Denied: Only Admins can view users.");
            notificationService.sendRoleSpecificNotification("Access Denied: Only User and Admin can view users.", requesterRole);
            return new HashMap<>();
        }
        return new HashMap<>(userRoles);
    }
}
