package carpincha.cApplicationService.mapper;

import carpincha.aCore.entity.activity.Activity;
import carpincha.cApplicationService.dto.activity.response.ActivityResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActivityResponseMapper {

    public ActivityResponse toResponse(Activity activity) {
        return new ActivityResponse(
                activity.getId(),
                activity.getTitle(),
                activity.getDescription(),
                activity.getCategory(),
                activity.getFrequency(),
                activity.getCustomFrequencyValue(),
                activity.getCustomFrequencyUnit(),
                activity.getStatus(),
                activity.getPriority(),
                activity.getCreatedAt(),
                activity.getEstimatedDuration(),
                activity.getDueMoment(),
                activity.getCompletedAt()
        );
    }

    public List<ActivityResponse> toResponseList(List<Activity> activities) {
        return activities.stream()
                .map(this::toResponse)
                .toList();
    }
}
