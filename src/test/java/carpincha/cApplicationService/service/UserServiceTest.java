package carpincha.cApplicationService.service;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.repoInterface.UserRepository;
import carpincha.aCore.serviceInterface.UserPasswordEncoder;
import carpincha.cApplicationService.dto.request.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserPasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    private RegisterRequest request;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Lola")
                .encodedPassword("encodedPss")
                .build();
        request = new RegisterRequest(user.getName(), "1234567");
    }

    @Test
    void whenRegisterUserWithValidData_thenSuccess() {
        when(repository.existsByName(request.name())).thenReturn(false);
        when(encoder.encode(request.password())).thenReturn("encodedPss");
        when(repository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(request);

        verify(repository).existsByName("Lola");
        verify(encoder).encode("1234567");
        verify(repository).save(any(User.class));

        assertNotNull(result);
        assertEquals("Lola", result.getName());
        assertEquals("encodedPss", result.getEncodedPassword());
    }
}
