package carpincha.dOutermostLayer.persistence.userEntity;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.repoInterface.UserRepository;
import carpincha.dOutermostLayer.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserRepositoryJpa repository;
    private final UserMapper mapper;

    @Override
    public User save(User user) {
        UserJpaEntity savedEntity = repository.save(mapper.toEntity(user));
        return mapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Optional<User> findByName(String name) {
        return repository.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }
}
