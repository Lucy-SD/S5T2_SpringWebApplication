package carpincha.dOutermostLayer.persistence;

import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.aCore.valueObject.FrequencyType;
import carpincha.aCore.valueObject.PriorityLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "activity")
public class ActivityJpaEntity {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_seq")
    @SequenceGenerator(name = "activity_seq", sequenceName = "activity_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private Boolean isTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FrequencyType frequency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PriorityLevel priority;

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private Duration estimatedDuration;

    @Column
    private Instant dueMoment;

    @Column
    private Instant completedAt;

    public ActivityJpaEntity(String title) {
        this.title = title;
        this.isTemplate = true;
        this.user = null;
        this.category = CategoryType.OTHER;
        this.frequency = FrequencyType.DAILY;
        this.status = ActivityStatus.PENDING;
        this.priority = PriorityLevel.MEDIUM;
        this.createdAt = Instant.now();
    }
}
