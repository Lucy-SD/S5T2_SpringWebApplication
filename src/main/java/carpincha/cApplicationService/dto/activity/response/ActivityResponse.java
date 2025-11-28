package carpincha.cApplicationService.dto.activity.response;

import carpincha.aCore.valueObject.*;

import java.time.Instant;

public record ActivityResponse(
        Long id,
        String title,
        String description,
        CategoryType category,
        FrequencyType frequency,
        Integer customFrequencyValue,
        FrequencyUnit customFrequencyUnit,
        ActivityStatus status,
        PriorityLevel priority,
        Instant createdAt,
        Integer estimatedDuration,
        Instant dueMoment,
        Instant completedAt
) {
}
