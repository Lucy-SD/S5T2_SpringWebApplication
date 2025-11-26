package carpincha.aCore.serviceInterface;

import carpincha.aCore.entity.user.User;
import carpincha.cApplicationService.dto.request.RegisterRequest;

import java.util.Optional;

public interface UserServiceContract {

    User registerUser(RegisterRequest request);
    User findByName(String name);
    Optional<User> findById(Long id);
}
