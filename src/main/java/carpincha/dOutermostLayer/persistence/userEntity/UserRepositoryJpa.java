package carpincha.dOutermostLayer.persistence.userEntity;

import carpincha.aCore.valueObject.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findByName(String name);
    boolean existsByName(String name);
    long countByRole(Role role);
}
