package carpincha.dOutermostLayer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryJpa extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findByName(String name);
    boolean existsByName(String name);
}
