package carpincha.aCore.repoInterface;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.valueObject.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    boolean existsByName(String name);
    Optional<User> findByName(String name);
    Optional<User> findById(Long id);
    List<User> findAll();
    long countByRole(Role role);
    void delete(User user);
}
