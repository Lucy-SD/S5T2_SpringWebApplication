package carpincha.dOutermostLayer.persistence.activityEntity;

import carpincha.aCore.valueObject.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepositoryJpa extends JpaRepository<ActivityJpaEntity, Long> {
    List<ActivityJpaEntity> findByIsTemplate(boolean isTemplate);
    List<ActivityJpaEntity> findByCategoryAndIsTemplate(CategoryType category, boolean isTemplate);
}
