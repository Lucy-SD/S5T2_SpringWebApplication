package carpincha.dOutermostLayer.security;

import carpincha.aCore.valueObject.Role;

public record UserPrincipal(
        Long userId,
        String name,
        Role role
) {
}
