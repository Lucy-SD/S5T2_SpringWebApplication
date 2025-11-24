package carpincha.dOutermostLayer.security;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.valueObject.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private final String SECRET_KEY = "EstaEsMiContraseñaSuperSecretaYLargaDe157VecesOcho¡!";
    private final long EXPIRATION = 3600000;
    private User user;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "secret", SECRET_KEY);
        ReflectionTestUtils.setField(jwtService, "expiration", EXPIRATION);
        user = User.builder()
                .name("Loli")
                .role(Role.USER)
                .build();
    }

    @Test
    void whenGenerateTokenWithValidCredentials_thenTokenIsGenerated() {
        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(3, token.split("\\.").length);

    }

    @Test
    void whenExtractNameFromValidToken_thenNameIsExtracted() {
        String token = jwtService.generateToken(user);
        String extractedName = jwtService.extractName(token);

        assertEquals("Loli", extractedName);
    }

    @Test
    void whenExtractRoleFromValidToken_thenRoleIsExtracted() {
        String token = jwtService.generateToken(user);
        Role extractedRole = jwtService.extractRole(token);

        assertEquals(Role.USER, extractedRole);
    }

    @Test
    void whenValidateValidToken_thenReturnsTrue() {
        String token = jwtService.generateToken(user);
        boolean isValid = jwtService.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void whenValidateInvalidToken_thenReturnsFalse() {
        String token = "not.valid.token";
        boolean isValid = jwtService.validateToken(token);

        assertFalse(isValid);
    }


}
