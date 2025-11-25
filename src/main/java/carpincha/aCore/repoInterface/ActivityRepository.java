package carpincha.aCore.repoInterface;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;

import java.util.List;

public interface ActivityRepository {

    List<Activity> findByUserId(Long userId);
    List<Activity> findByUserIdAndStatus(Long userId, ActivityStatus status);
    List<Activity> findByUserIdAndCategory(Long userId, CategoryType category);
    List<Activity> findByUserIdAndCategoryAndStatus(Long userId, CategoryType category, ActivityStatus status);
}
