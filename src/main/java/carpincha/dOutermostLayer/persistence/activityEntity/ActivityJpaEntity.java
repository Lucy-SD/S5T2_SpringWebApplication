package carpincha.dOutermostLayer.persistence.activityEntity;

import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.aCore.valueObject.FrequencyType;
import carpincha.aCore.valueObject.PriorityLevel;
import carpincha.dOutermostLayer.persistence.userEntity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activity")
public class ActivityJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_seq")
    @SequenceGenerator(name = "activity_seq", sequenceName = "activity_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isTemplate = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CategoryType category = CategoryType.OTHER;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private FrequencyType frequency =  FrequencyType.DAILY;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ActivityStatus status = ActivityStatus.PENDING;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PriorityLevel priority =  PriorityLevel.MEDIUM;

    @Column(nullable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column
    private Integer estimatedDuration;

    @Column
    private Instant dueMoment;

    @Column
    private Instant completedAt;

}
