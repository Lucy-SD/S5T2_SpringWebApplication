package carpincha.cApplicationService.service;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.serviceInterface.TokenService;
import carpincha.aCore.serviceInterface.UserPasswordEncoder;
import carpincha.aCore.serviceInterface.UserServiceContract;
import carpincha.aCore.valueObject.Role;
import carpincha.cApplicationService.dto.request.RegisterRequest;
import carpincha.cApplicationService.dto.response.JwtResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserServiceContract userService;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserPasswordEncoder enconder;

    @InjectMocks
    private AuthService authService;

    @Test
    void diagnosticTest_TokenServiceWorks() {
        User testUser = User.builder().name("test").build();
        when(tokenService.generateToken(any(User.class))).thenReturn("test-token");

        String result = tokenService.generateToken(testUser);

        assertThat(result).isEqualTo("test-token");
        verify(tokenService, times(1)).generateToken(testUser);
    }

    @Test
    void whenRegister_withValidData_ReturnsJwtResponse() {
        RegisterRequest request = new RegisterRequest("Loli", "1234567");

        User mockUser = User.builder()
                .name("Loli")
                .encodedPassword("encodedPassword")
                .role(Role.USER)
                .build();

        String mockToken = "mock.jwe.token";

        when(userService.registerUser(request)).thenReturn(mockUser);
        when(tokenService.generateToken(mockUser)).thenReturn(mockToken);

        JwtResponse response = authService.register(request);

        verify(userService, times(1)).registerUser(request);
        verify(tokenService, times(1)).generateToken(mockUser);

        assertThat(response).isNotNull();
        assertThat(response.token()).isEqualTo(mockToken);
        assertThat(response.name()).isEqualTo("Loli");
        assertThat(response.role()).isEqualTo(Role.USER);
        assertThat(response.type()).isEqualTo("Bearer");
    }
}
