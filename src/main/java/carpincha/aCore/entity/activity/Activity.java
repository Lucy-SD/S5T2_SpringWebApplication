package carpincha.aCore.entity.activity;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.valueObject.*;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import carpincha.cApplicationService.dto.activity.request.UpdateActivityRequest;
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
    private Integer customFrequencyValue;
    private FrequencyUnit customFrequencyUnit;
    @Builder.Default
    private ActivityStatus status = ActivityStatus.PENDING;
    @Builder.Default
    private PriorityLevel priority = PriorityLevel.MEDIUM;
    @Builder.Default
    private Instant createdAt = Instant.now();
    private Integer estimatedDuration;
    private Instant dueMoment;
    private Instant completedAt;

    public static Activity fromTemplateRequest(CreateActivityRequest request) {
        return Activity.builder()
                .title(request.title())
                .description(request.description())
                .isTemplate(true)
                .user(null)
                .category(request.category() != null ? request.category() : CategoryType.OTHER)
                .frequency(request.frequency() != null ? request.frequency() : FrequencyType.DAILY)
                .customFrequencyValue(request.customFrequencyValue())
                .customFrequencyUnit(request.customFrequencyUnit())
                .priority(request.priority() != null ? request.priority() : PriorityLevel.MEDIUM)
                .estimatedDuration(request.estimatedDuration())
                .dueMoment(request.dueMoment())
                .build();
    }

    public static Activity fromTemplate(Activity template, User user) {
        return Activity.builder()
                .id(null)
                .title(template.getTitle())
                .description(template.getDescription())
                .isTemplate(false)
                .user(user)
                .category(template.getCategory())
                .frequency(template.getFrequency())
                .customFrequencyValue(template.getCustomFrequencyValue())
                .customFrequencyUnit(template.getCustomFrequencyUnit())
                .status(template.getStatus())
                .priority(template.getPriority())
                .createdAt(Instant.now())
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
                .customFrequencyValue(request.customFrequencyValue())
                .customFrequencyUnit(request.customFrequencyUnit())
                .status(ActivityStatus.PENDING)
                .priority(request.priority() != null ? request.priority() : PriorityLevel.MEDIUM)
                .createdAt(Instant.now())
                .estimatedDuration(request.estimatedDuration())
                .dueMoment(request.dueMoment())
                .build();
    }

    public Activity withUpdate(UpdateActivityRequest request) {
        return Activity.builder()
                .id(this.id)
                .title(request.title() != null ? request.title() : this.title)
                .description(request.description() != null ? request.description() : this.description)
                .isTemplate(this.isTemplate)
                .user(this.user)
                .category(request.category() != null ? request.category() : this.category)
                .frequency(request.frequency() != null ? request.frequency() : this.frequency)
                .customFrequencyValue(request.customFrequencyValue() != null ? request.customFrequencyValue() : this.customFrequencyValue)
                .customFrequencyUnit(request.customFrequencyUnit() != null ? request.customFrequencyUnit() : this.customFrequencyUnit)
                .status(request.status() != null ? request.status() : this.status)
                .priority(request.priority() != null ? request.priority() : this.priority)
                .createdAt(this.createdAt)
                .estimatedDuration(request.estimatedDuration() != null ? request.estimatedDuration() : this.estimatedDuration)
                .dueMoment(request.dueMoment() != null ? request.dueMoment() : this.dueMoment)
                .completedAt(this.completedAt)
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
                .customFrequencyValue(this.customFrequencyValue)
                .customFrequencyUnit(this.customFrequencyUnit)
                .status(status)
                .priority(this.priority)
                .createdAt(this.createdAt)
                .estimatedDuration(this.estimatedDuration)
                .dueMoment(this.dueMoment)
                .completedAt(status == ActivityStatus.COMPLETED ? Instant.now() : null)
                .build();
    }
}
