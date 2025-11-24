package carpincha.cApplicationService.service;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.repoInterface.UserRepository;
import carpincha.aCore.serviceInterface.UserPasswordEncoder;
import carpincha.aCore.serviceInterface.UserServiceContract;
import carpincha.cApplicationService.dto.request.RegisterRequest;
import carpincha.aCore.exception.NameAlreadyExistsException;
import carpincha.aCore.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
