package carpincha.cApplicationService.dto.activity.request;

import carpincha.aCore.valueObject.CategoryType;
import carpincha.aCore.valueObject.FrequencyType;
import carpincha.aCore.valueObject.PriorityLevel;
import jakarta.validation.constraints.NotBlank;

import java.time.Duration;
import java.time.Instant;

public record CreateActivityRequest(
        @NotBlank(message = "El título de la actividad es obligatorio.")
        String title,

        String description,

        CategoryType category,
        FrequencyType frequency,
        PriorityLevel priority,
        Duration estimatedDuration,
        Instant dueMoment
) {
}
