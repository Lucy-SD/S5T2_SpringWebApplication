package carpincha.dOutermostLayer.persistence.activityEntity;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.repoInterface.ActivityRepository;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.dOutermostLayer.persistence.mapper.ActivityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ActivityRepositoryAdapter implements ActivityRepository {

    private final ActivityRepositoryJpa repository;
    private final ActivityMapper mapper;

    @Override
    public Activity save(Activity activity) {
        ActivityJpaEntity savedActivity = repository.save(mapper.toEntity(activity));
        return mapper.toDomain(savedActivity);
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public boolean deleteById(Long id) {
        if (this.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public List<Activity> findByIsTemplate(boolean isTemplate) {
        return repository.findByIsTemplate(isTemplate).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Activity> findByCategoryAndIsTemplate(CategoryType category, boolean isTemplate) {
        return repository.findByCategoryAndIsTemplate(category, isTemplate).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Activity> findByStatusAndIsTemplate(ActivityStatus status, boolean isTemplate) {
        return repository.findByStatusAndIsTemplate(status, isTemplate).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Activity> findByUserIdAndIsTemplate(Long userId, boolean isTemplate) {
        return repository.findByUser_IdAndIsTemplate(userId, isTemplate).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Activity> findByUserIdAndStatusAndIsTemplate(Long userId, ActivityStatus status, boolean isTemplate) {
        return repository.findByUser_IdAndStatusAndIsTemplate(userId, status, isTemplate).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Activity> findByUserIdAndCategoryAndIsTemplate(Long userId, CategoryType category, boolean isTemplate) {
        return repository.findByUser_IdAndCategoryAndIsTemplate(userId, category, isTemplate).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByTitleAndUserIdAndIsTemplate(String title, Long userId, boolean isTemplate) {
        return repository.existsByTitleAndUser_IdAndIsTemplate(title, userId, isTemplate);
    }
}
