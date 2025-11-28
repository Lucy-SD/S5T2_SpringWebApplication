package carpincha.cApplicationService.dto.activity.request;

import carpincha.aCore.valueObject.*;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public record UpdateActivityRequest(
        String title,
        String description,
        CategoryType category,
        FrequencyType frequency,

        @Positive(message = "El valor de la frecuencia debe ser mayor a cero.")
        Integer customFrequencyValue,

        FrequencyUnit customFrequencyUnit,
        ActivityStatus status,
        PriorityLevel priority,
        Integer estimatedDuration,
        Instant dueMoment
) {
}
