package carpincha.aCore.entity.user;

import carpincha.aCore.valueObject.Role;
import lombok.*;

import java.time.Instant;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private String encodedPassword;
    @Builder.Default
    @Setter
    private Role role = Role.USER;
    @Builder.Default
    private Instant createdAt = Instant.now();

    public User withRole(Role newRole) {
        return this.toBuilder()
                .role(newRole)
                .build();
    }
}
