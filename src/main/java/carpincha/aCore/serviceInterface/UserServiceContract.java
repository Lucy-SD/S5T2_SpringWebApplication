package carpincha.aCore.serviceInterface;

import carpincha.aCore.entity.user.User;
import carpincha.cAppliationService.dto.request.RegisterRequest;

public interface UserServiceContract {

    User registerUser(RegisterRequest request);
    User findByName(String name);
}
