package carpincha.dOutermostLayer.persistence;

import carpincha.aCore.valueObject.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_db")
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_db_seq")
    @SequenceGenerator(name = "user_db_seq", sequenceName = "user_db_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 60, nullable = false)
    private String encodedPassword;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private Instant createdAt;

    public UserJpaEntity(String name, String encodedPassword) {
        this.name = name;
        this.encodedPassword = encodedPassword;
        this.role = Role.USER;
        this.createdAt = Instant.now();
    }

    public void setId(Long id) { this.id = id; }
}
