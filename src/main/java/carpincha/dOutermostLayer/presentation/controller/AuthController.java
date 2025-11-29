package carpincha.dOutermostLayer.presentation.controller;

import carpincha.cApplicationService.dto.user.request.LoginRequest;
import carpincha.cApplicationService.dto.user.request.RegisterRequest;
import carpincha.cApplicationService.dto.user.response.UserInfoResponse;
import carpincha.cApplicationService.dto.validation.JwtResponse;
import carpincha.cApplicationService.service.AuthService;
import carpincha.dOutermostLayer.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest request) {
        JwtResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        JwtResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> me(@AuthenticationPrincipal UserPrincipal principal) {
        UserInfoResponse resp = new UserInfoResponse(principal.userId(), principal.name(), principal.role());
        return ResponseEntity.ok(resp);
    }
}
