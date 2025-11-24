package carpincha.aCore.serviceInterface;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.valueObject.Role;

public interface TokenService {
    String generateToken(User user);
    boolean validateToken(String token);
    String extractName(String token);
    Role extractRole(String token);
}
