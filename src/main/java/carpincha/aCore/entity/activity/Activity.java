package carpincha.aCore.entity.activity;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.valueObject.*;
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

    public static Activity fromTemplateRequest(ActivityParams params) {
        return Activity.builder()
                .title(params.title())
                .description(params.description())
                .isTemplate(true)
                .user(null)
                .category(params.category() != null ? params.category() : CategoryType.OTHER)
                .frequency(params.frequency() != null ? params.frequency() : FrequencyType.DAILY)
                .customFrequencyValue(params.customFrequencyValue())
                .customFrequencyUnit(params.customFrequencyUnit())
                .priority(params.priority() != null ? params.priority() : PriorityLevel.MEDIUM)
                .estimatedDuration(params.estimatedDuration())
                .dueMoment(params.dueMoment())
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

    public static Activity fromRequest(ActivityParams params, User user) {
        return Activity.builder()
                .title(params.title())
                .description(params.description())
                .isTemplate(false)
                .user(user)
                .category(params.category() != null ? params.category() : CategoryType.OTHER)
                .frequency(params.frequency() != null ? params.frequency() : FrequencyType.DAILY)
                .customFrequencyValue(params.customFrequencyValue())
                .customFrequencyUnit(params.customFrequencyUnit())
                .status(ActivityStatus.PENDING)
                .priority(params.priority() != null ? params.priority() : PriorityLevel.MEDIUM)
                .createdAt(Instant.now())
                .estimatedDuration(params.estimatedDuration())
                .dueMoment(params.dueMoment())
                .build();
    }

    public Activity withUpdate(ActivityParams params) {
        return Activity.builder()
                .id(this.id)
                .title(params.title() != null ? params.title() : this.title)
                .description(params.description() != null ? params.description() : this.description)
                .isTemplate(this.isTemplate)
                .user(this.user)
                .category(params.category() != null ? params.category() : this.category)
                .frequency(params.frequency() != null ? params.frequency() : this.frequency)
                .customFrequencyValue(params.customFrequencyValue() != null ? params.customFrequencyValue() : this.customFrequencyValue)
                .customFrequencyUnit(params.customFrequencyUnit() != null ? params.customFrequencyUnit() : this.customFrequencyUnit)
                .status(params.status() != null ? params.status() : this.status)
                .priority(params.priority() != null ? params.priority() : this.priority)
                .createdAt(this.createdAt)
                .estimatedDuration(params.estimatedDuration() != null ? params.estimatedDuration() : this.estimatedDuration)
                .dueMoment(params.dueMoment() != null ? params.dueMoment() : this.dueMoment)
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
