package osgi.authenticationproducer;

import java.util.Map;

public interface UserProfileService {
	void registerUser(String username, String password, String role);
    boolean loginUser(String username, String password);
    void logoutUser(String username);
    String getUserRole(String username);
    void changeUsername(String requesterUsername, String oldUsername, String newUsername);
    void changeUserRole(String requesterUsername, String targetUsername, String newRole);
    Map<String, String> listAllUsers(String requesterUsername);
}
