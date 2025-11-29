package carpincha.cApplicationService.dto.user.response;

import carpincha.aCore.valueObject.Role;

public record UserInfoResponse(
        Long id,
        String name,
        Role role
) {
}
