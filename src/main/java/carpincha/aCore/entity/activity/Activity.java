package carpincha.aCore.entity.activity;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.aCore.valueObject.FrequencyType;
import carpincha.aCore.valueObject.PriorityLevel;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Boolean isTemplate = false;
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
    private Integer estimatedDuration;
    private Instant dueMoment;
    private Instant completedAt;

    public static Activity fromTemplate(Activity template, User user) {
        return Activity.builder()
                .id(null)
                .title(template.getTitle())
                .description(template.getDescription())
                .isTemplate(false)
                .user(user)
                .category(template.getCategory())
                .priority(template.getPriority())
                .estimatedDuration(template.getEstimatedDuration())
                .dueMoment(template.getDueMoment())
                .build();
    }

    public static Activity fromRequest(CreateActivityRequest request, User user) {
        return Activity.builder()
                .title(request.title())
                .description(request.description())
                .isTemplate(false)
                .user(user)
                .category(request.category() != null ? request.category() : CategoryType.OTHER)
                .frequency(request.frequency() != null ? request.frequency() : FrequencyType.DAILY)
                .status(ActivityStatus.PENDING)
                .priority(request.priority() != null ? request.priority() : PriorityLevel.MEDIUM)
                .createdAt(Instant.now())
                .estimatedDuration(request.estimatedDuration())
                .dueMoment(request.dueMoment())
                .build();
    }

    public Activity withStatus(ActivityStatus status) {
        return Activity.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .isTemplate(this.isTemplate)
                .user(this.user)
                .category(this.category)
                .frequency(this.frequency)
                .status(status)
                .priority(this.priority)
                .createdAt(this.createdAt)
                .estimatedDuration(this.estimatedDuration)
                .dueMoment(this.dueMoment)
                .completedAt(status == ActivityStatus.COMPLETED ? Instant.now() : null)
                .build();
    }
}
