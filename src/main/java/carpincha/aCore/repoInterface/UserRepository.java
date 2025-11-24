package carpincha.aCore.repoInterface;

import carpincha.aCore.entity.user.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByName(String name);
    Optional<User> findByName(String name);
    User save(User user);
}
