package carpincha.dOutermostLayer.persistence.userEntity;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.exception.NotFoundException;
import carpincha.aCore.repoInterface.UserRepository;
import carpincha.aCore.valueObject.Role;
import carpincha.dOutermostLayer.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public List<User> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void updateRole(Long id, Role role) {
        UserJpaEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario"));
        entity.setRole(role);
        repository.save(entity);
    }

    @Override
    public long countByRole(Role role) {
        return repository.countByRole(role);
    }

    @Override
    public void delete(User user) {
        repository.findById(user.getId())
                .ifPresent(repository::delete);
    }
}
