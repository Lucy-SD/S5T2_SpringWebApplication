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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    private Long id;
    private User user;
    private String title;
    private String description;
    @Builder.Default
    private CategoryType category = CategoryType.OTHER;
    @Builder.Default
    private FrequencyType frequency = FrequencyType.DAILY;
    @Builder.Default
    private ActivityStatus status = ActivityStatus.PENDING;
    private PriorityLevel priority;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private Integer estimatedDuration;
    private LocalDate dueDate;
    private LocalDateTime completedAt;

}
