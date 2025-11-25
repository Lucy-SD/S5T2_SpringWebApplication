package carpincha.dOutermostLayer.persistence.activityEntity;

import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepositoryJpa extends JpaRepository<ActivityJpaEntity, Long> {

    List<ActivityJpaEntity> findByIsTemplate(boolean isTemplate);
    List<ActivityJpaEntity> findByCategoryAndIsTemplate(CategoryType category, boolean isTemplate);

    List<ActivityJpaEntity> findByUser_IdAndIsTemplate(Long userId, boolean isTemplate);
    List<ActivityJpaEntity> findByUser_IdAndStatusAndIsTemplate(Long userId, ActivityStatus status, boolean isTemplate);
    List<ActivityJpaEntity> findByUser_IdAndCategoryAndIsTemplate(Long userId, CategoryType category, boolean isTemplate);
    boolean existsByTitleAndUser_IdAndIsTemplate (String title, Long userId, boolean isTemplate);

}
