package carpincha.dOutermostLayer.persistence.mapper;

import carpincha.aCore.entity.activity.Activity;
import carpincha.dOutermostLayer.persistence.activityEntity.ActivityJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityMapper {

    private final UserMapper userMapper;

    public ActivityJpaEntity toEntity(Activity activity) {
        ActivityJpaEntity entity = ActivityJpaEntity.builder()
                .title(activity.getTitle())
                .description(activity.getDescription())
                .isTemplate(activity.getIsTemplate())
                .user(activity.getUser() != null ? userMapper.toEntity(activity.getUser()) : null)
                .category(activity.getCategory())
                .frequency(activity.getFrequency())
                .status(activity.getStatus())
                .priority(activity.getPriority())
                .createdAt(activity.getCreatedAt())
                .estimatedDuration(activity.getEstimatedDuration())
                .dueMoment(activity.getDueMoment())
                .completedAt(activity.getCompletedAt())
                .build();
        if (activity.getId() != null) {
            entity.setId(activity.getId());
        }
        return entity;
    }

    public Activity toDomain(ActivityJpaEntity entity) {
        return Activity.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .isTemplate(entity.getIsTemplate())
                .user(entity.getUser() != null ? userMapper.toDomain(entity.getUser()) : null)
                .category(entity.getCategory())
                .frequency(entity.getFrequency())
                .status(entity.getStatus())
                .priority(entity.getPriority())
                .createdAt(entity.getCreatedAt())
                .estimatedDuration(entity.getEstimatedDuration())
                .dueMoment(entity.getDueMoment())
                .completedAt(entity.getCompletedAt())
                .build();
    }
}
