package carpincha.aCore.repoInterface;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository {

    Activity save(Activity activity);
    Optional<Activity> findById(Long id);
    boolean deleteById(Long id);
    boolean existsById(Long id);

    List<Activity> findByIsTemplate(boolean isTemplate);
    List<Activity> findByCategoryAndIsTemplate(CategoryType category, boolean isTemplate);

    List<Activity> findByUserIdAndIsTemplate(Long userId, boolean isTemplate);
    List<Activity> findByUserIdAndStatusAndIsTemplate(Long userId, ActivityStatus status, boolean isTemplate);
    List<Activity> findByUserIdAndCategoryAndIsTemplate(Long userId, CategoryType category, boolean isTemplate);
    boolean existsByTitleAndUserIdAndIsTemplate(String title, Long userId, boolean isTemplate);
}
