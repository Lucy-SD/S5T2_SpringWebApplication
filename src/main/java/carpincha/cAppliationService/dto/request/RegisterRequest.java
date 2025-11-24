package carpincha.cAppliationService.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio.")
        String name,
        @NotBlank(message = "La contraseña es obligatoria y debe contener al menos 7 caracteres.")
        @Size(min = 7, message = "Ingrese una contraseña de al menos 7 caracteres.")
        String password
) {
}
