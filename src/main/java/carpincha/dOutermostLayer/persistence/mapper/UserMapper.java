package carpincha.dOutermostLayer.persistence.mapper;

import carpincha.aCore.entity.user.User;
import carpincha.dOutermostLayer.persistence.userEntity.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserJpaEntity toEntity (User user) {
        UserJpaEntity entity = new UserJpaEntity(
                user.getName(),
                user.getEncodedPassword()
        );
        entity.setId(user.getId());
        return entity;
    }

    public User toDomain (UserJpaEntity entity) {
      return User.builder()
              .id(entity.getId())
              .name(entity.getName())
              .encodedPassword(entity.getEncodedPassword())
              .role(entity.getRole())
              .createdAt(entity.getCreatedAt())
              .build();
    }
}
