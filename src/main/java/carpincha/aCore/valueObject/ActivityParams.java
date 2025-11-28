package carpincha.aCore.valueObject;

import carpincha.aCore.exception.InvalidDataException;

import java.time.Instant;

public record ActivityParams(
        String title,
        String description,
        CategoryType category,
        FrequencyType frequency,
        Integer customFrequencyValue,
        FrequencyUnit customFrequencyUnit,
        ActivityStatus status,
        PriorityLevel priority,
        Integer estimatedDuration,
        Instant dueMoment
) {
    public ActivityParams {
        if (frequency() == FrequencyType.CUSTOM && (customFrequencyValue() == null || customFrequencyUnit() == null)) {
            throw new InvalidDataException("La frecuencia personalizada requiere tiempo y unidad.");
        }
    }
}
