package carpincha.cApplicationService.service;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.serviceInterface.TokenService;
import carpincha.aCore.serviceInterface.UserPasswordEncoder;
import carpincha.aCore.serviceInterface.UserServiceContract;
import carpincha.cApplicationService.dto.user.request.LoginRequest;
import carpincha.cApplicationService.dto.user.request.RegisterRequest;
import carpincha.cApplicationService.dto.response.JwtResponse;
import carpincha.aCore.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceContract userService;
    private final TokenService tokenService;
    private final UserPasswordEncoder encoder;

    public JwtResponse register(RegisterRequest request) {
        User user = userService.registerUser(request);
        String token = tokenService.generateToken(user);
        return new JwtResponse(token, user.getName(), user.getRole());
    }

    public JwtResponse login(LoginRequest request) {
        User user = userService.findByName(request.name());

        if(!encoder.matches(request.password(), user.getEncodedPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = tokenService.generateToken(user);
        return new JwtResponse(token, user.getName(), user.getRole());
    }
}
