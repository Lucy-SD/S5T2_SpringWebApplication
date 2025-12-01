package carpincha.cApplicationService.service;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.repoInterface.UserRepository;
import carpincha.aCore.serviceInterface.UserPasswordEncoder;
import carpincha.aCore.serviceInterface.UserServiceContract;
import carpincha.aCore.valueObject.Role;
import carpincha.cApplicationService.dto.user.request.RegisterRequest;
import carpincha.aCore.exception.NameAlreadyExistsException;
import carpincha.aCore.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserServiceContract {

    private final UserRepository repository;
    private final UserPasswordEncoder encoder;

    @Override
    public User registerUser(RegisterRequest request) {
        if (repository.existsByName(request.name())) throw new NameAlreadyExistsException();

        String encodedPassword = encoder.encode(request.password());
        User user = User.builder().name(request.name()).encodedPassword(encodedPassword).build();
        User savedUser = repository.save(user);

        log.info("Usuario '{}' registrado correctamente.", request.name());

        return savedUser;
    }

    @Override
    public User findByName(String name) {
        Optional<User> user = repository.findByName(name);
        return user.orElseThrow(() -> new NotFoundException("Usuario"));
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public void changeUserRole(Long id, Role role) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario"));
        User updatedUser = user.withRole(role);
        repository.save(updatedUser);
        log.info("Rol del usuario '{}' actualizado correctamente a '{}'.", user.getName(), role);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario"));

        if (user.getRole() == Role.ADMIN) {
            long adminCount = repository.countByRole(Role.ADMIN);
            if (adminCount <= 1) {
                throw new RuntimeException("No se puede borrar el único administrador del sistema.");
            }
        }
        repository.delete(user);
        log.info("Usuario '{}' eliminado correctamente.", user.getName());

    }
}
