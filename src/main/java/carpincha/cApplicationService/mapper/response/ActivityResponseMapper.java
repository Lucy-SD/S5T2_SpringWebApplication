package carpincha.cApplicationService.mapper.response;

import carpincha.aCore.entity.activity.Activity;
import carpincha.cApplicationService.dto.activity.response.ActivityResponse;
import carpincha.cApplicationService.dto.user.response.UserInfoResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActivityResponseMapper {

    public ActivityResponse toResponse(Activity activity) {
       UserInfoResponse userInfo = null;
       if (activity.getUser() != null) {
           userInfo = new UserInfoResponse(
                       activity.getUser().getId(),
                       activity.getUser().getName(),
                       activity.getUser().getRole()
           );
       }

        return new ActivityResponse(
                activity.getId(),
                activity.getTitle(),
                activity.getDescription(),
                userInfo,
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
