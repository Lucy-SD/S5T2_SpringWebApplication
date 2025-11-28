package carpincha.aCore.serviceInterface;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import carpincha.cApplicationService.dto.activity.request.UpdateActivityRequest;

import java.util.List;

public interface ActivityServiceContract {

    List<Activity> findAllActivities(ActivityStatus status, CategoryType category);
    List<Activity> findAllTemplates(CategoryType category);
    Activity findActivityByIdAdmin(Long id);

    Activity cloneTemplate(Long id, Long userId);

    Activity createActivity(CreateActivityRequest request, Long userId);
    Activity findActivityById(Long id, Long userId);
    Activity updateActivity(Long id, UpdateActivityRequest request, Long userId);
    Activity completeActivity(Long id, Long userId);
    Activity uncompleteActivity(Long id, Long userId);
    boolean deleteActivity(Long id, Long userId);

    List<Activity> findActivitiesByUserId(Long userId);
    List<Activity> findActivitiesByUserIdAndStatus(Long userId, ActivityStatus status);
    List<Activity> findActivitiesByUserIdAndCategory(Long userId, CategoryType category);
}
