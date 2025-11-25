package carpincha.dOutermostLayer.persistence.activityEntity;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.repoInterface.ActivityRepository;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ActivityRepositoryAdapter implements ActivityRepository {
    @Override
    public Activity save(Activity activity) {
        return null;
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public List<Activity> findByIsTemplate(boolean isTemplate) {
        return List.of();
    }

    @Override
    public List<Activity> findByCategoryAndIsTemplate(CategoryType category, boolean isTemplate) {
        return List.of();
    }

    @Override
    public List<Activity> findByUserIdAndIsTemplate(Long userId, boolean isTemplate) {
        return List.of();
    }

    @Override
    public List<Activity> findByUserIdStatusAndIsTemplate(Long userId, ActivityStatus status, boolean isTemplate) {
        return List.of();
    }

    @Override
    public List<Activity> findByUserIdCategoryAndIsTemplate(Long userId, CategoryType category, boolean isTemplate) {
        return List.of();
    }

    @Override
    public boolean existsByTitleUserIdAndIsTemplate(String title, Long userId, boolean isTemplate) {
        return false;
    }
}
