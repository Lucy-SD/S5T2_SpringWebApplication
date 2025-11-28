package carpincha.cApplicationService.mapper.request;

import carpincha.aCore.valueObject.ActivityParams;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import carpincha.cApplicationService.dto.activity.request.UpdateActivityRequest;
import org.springframework.stereotype.Component;

@Component
public class ActivityRequestMapper {

    public ActivityParams toParams(CreateActivityRequest request) {
        return new ActivityParams(
                request.title(),
                request.description(),
                request.category(),
                request.frequency(),
                request.customFrequencyValue(),
                request.customFrequencyUnit(),
                ActivityStatus.PENDING,
                request.priority(),
                request.estimatedDuration(),
                request.dueMoment()
        );
    }
    public ActivityParams toParams(UpdateActivityRequest request) {
        return new ActivityParams(
                request.title(),
                request.description(),
                request.category(),
                request.frequency(),
                request.customFrequencyValue(),
                request.customFrequencyUnit(),
                request.status(),
                request.priority(),
                request.estimatedDuration(),
                request.dueMoment()
        );
    }
}
