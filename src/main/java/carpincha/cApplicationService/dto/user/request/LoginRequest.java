package carpincha.cApplicationService.dto.user.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio.")
        String name,
        @NotBlank
        @NotBlank(message = "La contraseña es obligatoria.")
        String password
) {
}
