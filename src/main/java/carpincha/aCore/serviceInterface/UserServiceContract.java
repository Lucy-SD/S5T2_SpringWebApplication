package carpincha.aCore.serviceInterface;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.valueObject.Role;
import carpincha.cApplicationService.dto.user.request.RegisterRequest;

import java.util.List;
import java.util.Optional;

public interface UserServiceContract {

    User registerUser(RegisterRequest request);
    User findByName(String name);
    Optional<User> findById(Long id);

    List<User> findAllUsers();
    void changeUserRole(Long id, Role role);
    void deleteUser(Long id);
}
