package carpincha.cApplicationService.dto.activity.response;

import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.aCore.valueObject.FrequencyType;
import carpincha.aCore.valueObject.PriorityLevel;

import java.time.Instant;

public record ActivityResponse(
        Long id,
        String title,
        String description,
        CategoryType category,
        FrequencyType frequency,
        ActivityStatus status,
        PriorityLevel priority,
        Instant createdAt,
        Integer estimatedDuration,
        Instant dueMoment,
        Instant completedAt
) {
}
