package carpincha.aCore.entity.activity;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.aCore.valueObject.FrequencyType;
import carpincha.aCore.valueObject.PriorityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    private Long id;
    private String title;
    private String description;
    @Builder.Default
    private Boolean istTemplate = true;
    @Builder.Default
    private User user = null;
    @Builder.Default
    private CategoryType category = CategoryType.OTHER;
    @Builder.Default
    private FrequencyType frequency = FrequencyType.DAILY;
    @Builder.Default
    private ActivityStatus status = ActivityStatus.PENDING;
    @Builder.Default
    private PriorityLevel priority = PriorityLevel.MEDIUM;
    @Builder.Default
    private Instant createdAt = Instant.now();
    private Duration estimatedDuration;
    private Instant dueMoment;
    private Instant completedAt;

}
