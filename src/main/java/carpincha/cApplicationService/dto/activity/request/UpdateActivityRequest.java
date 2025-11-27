package carpincha.cApplicationService.dto.activity.request;

import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.aCore.valueObject.FrequencyType;
import carpincha.aCore.valueObject.PriorityLevel;

import java.time.Instant;

public record UpdateActivityRequest(
        String title,
        String description,
        CategoryType category,
        FrequencyType frequency,
        ActivityStatus status,
        PriorityLevel priority,
        Integer estimatedDuration,
        Instant dueMoment
) {
}
