package carpincha.aCore.entity.user;

import carpincha.aCore.valueObject.Role;
import lombok.*;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private String encodedPassword;
    @Builder.Default
    private Role role = Role.USER;
    @Builder.Default
    private Instant createdAt = Instant.now();
}
