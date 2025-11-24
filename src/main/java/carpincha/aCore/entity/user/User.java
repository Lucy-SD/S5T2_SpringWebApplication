package carpincha.aCore.entity.user;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.entity.animal.Animal;
import carpincha.aCore.valueObject.Role;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    @Builder.Default
    private List<Animal> animals = new ArrayList<>();
    @Builder.Default
    private List<Activity> activities = new ArrayList<>();
}
