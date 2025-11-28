package carpincha.cApplicationService.service;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.entity.user.User;
import carpincha.aCore.exception.InvalidDataException;
import carpincha.aCore.exception.NameAlreadyExistsException;
import carpincha.aCore.exception.NotFoundException;
import carpincha.aCore.repoInterface.ActivityRepository;
import carpincha.aCore.repoInterface.UserRepository;
import carpincha.aCore.serviceInterface.ActivityServiceContract;
import carpincha.aCore.valueObject.ActivityParams;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import carpincha.cApplicationService.dto.activity.request.UpdateActivityRequest;
import carpincha.cApplicationService.mapper.request.ActivityRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService implements ActivityServiceContract {

    private final ActivityRepository repository;
    private final UserRepository userRepository;
    private final ActivityRequestMapper mapper;


    @Override
    public List<Activity> findAllActivities(ActivityStatus status, CategoryType category) {
        if (status != null && category != null) {
            throw new InvalidDataException("Seleccione un solo filtro.");
        }
        if (status != null) {
            return repository.findByStatusAndIsTemplate(status, false);
        }
        if (category != null) {
            return repository.findByCategoryAndIsTemplate(category, false);
        }
        return repository.findByIsTemplate(false);
    }

    @Override
    public List<Activity> findAllTemplates(CategoryType category) {
        if (category != null) {
            return repository.findByCategoryAndIsTemplate(category, true);
        }
        return repository.findByIsTemplate(true);
    }

    @Override
    public Activity findActivityByIdAdmin(Long id) {
        Activity activity = repository.findById(id).orElseThrow(() -> new NotFoundException("Actividad"));

        if (!activity.getIsTemplate()) log.warn("Cuidado, esta es una actividad de un usuario.");

        return activity;
    }

    @Override
    public Activity cloneTemplate(Long id, Long userId) {
        Activity activity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plantilla"));

        if (!activity.getIsTemplate()) throw new InvalidDataException("El recurso solicitado no es una plantilla.");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario"));

        Activity clonedActivity = Activity.fromTemplate(activity, user);
        Activity savedActivity = repository.save(clonedActivity);

        log.info("Actividad personalizada '{}' creada correctamente.", savedActivity.getTitle());

        return savedActivity;
    }

    @Override
    public Activity createActivity(CreateActivityRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario"));

        if (repository.existsByTitleAndUserIdAndIsTemplate(request.title(), userId, false))
            throw new NameAlreadyExistsException();

        ActivityParams params = mapper.toParams(request);
        Activity activity = Activity.fromRequest(params, user);
        Activity savedActivity = repository.save(activity);

        log.info("Actividad '{}' creada correctamente.", request.title());

        return savedActivity;
    }


    @Override
    public Activity findActivityById(Long id, Long userId) {
        Activity activity = repository.findById(id).orElseThrow(() -> new NotFoundException("Actividad"));

        if (!activity.getUser().getId().equals(userId)) throw new NotFoundException("Actividad");

        log.info("Actividad encontrada.");

        return activity;
    }

    @Override
    public Activity updateActivity(Long id, UpdateActivityRequest request, Long userId) {
        ActivityParams params = mapper.toParams(request);
        Activity activity = findActivityById(id, userId);
        Activity updatedActivity = activity.withUpdate(params);

        repository.save(updatedActivity);
        log.info("Actividad actualizada correctamente.");

        return updatedActivity;
    }

    @Override
    public Activity completeActivity(Long id, Long userId) {
        Activity activity = findActivityById(id, userId);
        Activity completed = activity.withStatus(ActivityStatus.COMPLETED);

        repository.save(completed);
        log.info("Actividad completada exitosamente. Felicidades ¡!");

        return completed;
    }

    @Override
    public Activity uncompleteActivity(Long id, Long userId) {
        Activity activity = findActivityById(id, userId);

        Activity notCompleted = activity.withStatus(ActivityStatus.PENDING);

        repository.save(notCompleted);
        log.info("Se marcó la actividad como pendiente.");

        return notCompleted;
    }

    @Override
    public boolean deleteActivity(Long id, Long userId) {
        findActivityById(id, userId);
        boolean deleted = repository.deleteById(id);
        if (deleted) log.info("Actividad eliminada correctamente.");
        return deleted;
    }

    @Override
    public List<Activity> findActivitiesByUserId(Long userId) {
        return repository.findByUserIdAndIsTemplate(userId, false);
    }

    @Override
    public List<Activity> findActivitiesByUserIdAndStatus(Long userId, ActivityStatus status) {
        return repository.findByUserIdAndStatusAndIsTemplate(userId, status, false);
    }

    @Override
    public List<Activity> findActivitiesByUserIdAndCategory(Long userId, CategoryType category) {
        return repository.findByUserIdAndCategoryAndIsTemplate(userId, category, false);
    }
}
