package osgi.notificationproducer;

public interface NotificationService {
	void sendNotification(String message);
	void sendCustomNotification(String username, String action);
	void sendRoleSpecificNotification(String message, String role);
}
