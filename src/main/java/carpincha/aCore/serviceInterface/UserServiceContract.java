package carpincha.aCore.serviceInterface;

import carpincha.aCore.entity.user.User;
import carpincha.cApplicationService.dto.request.RegisterRequest;

public interface UserServiceContract {

    User registerUser(RegisterRequest request);
    User findByName(String name);
}
