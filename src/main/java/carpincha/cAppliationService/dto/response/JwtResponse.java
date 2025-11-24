package carpincha.cAppliationService.dto.response;

import carpincha.aCore.valueObject.Role;

public record JwtResponse(
        String token,
        String type,
        String name,
        Role role
){
    public JwtResponse (String token, String name, Role role) {
        this(token, "Bearer", name, role);
    }
}
