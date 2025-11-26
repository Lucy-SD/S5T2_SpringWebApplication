package carpincha.cApplicationService.service;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.entity.user.User;
import carpincha.aCore.exception.InvalidDataException;
import carpincha.aCore.exception.NameAlreadyExistsException;
import carpincha.aCore.exception.NotFoundException;
import carpincha.aCore.repoInterface.ActivityRepository;
import carpincha.aCore.serviceInterface.ActivityServiceContract;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.aCore.valueObject.FrequencyType;
import carpincha.aCore.valueObject.PriorityLevel;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import carpincha.cApplicationService.dto.activity.request.UpdateActivityRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService implements ActivityServiceContract {

    private final ActivityRepository repository;
    private final UserService userService;

    @Override
    public Activity createTemplate(CreateActivityRequest request) {
        if (repository.existsByTitleAndUserIdAndIsTemplate(request.title(), null, true))
            throw new NameAlreadyExistsException();

        Activity activity = Activity.builder()
                .title(request.title())
                .description(request.description())
                .isTemplate(true)
                .user(null)
                .category(request.category() != null ? request.category() : CategoryType.OTHER)
                .frequency(request.frequency() != null ? request.frequency() : FrequencyType.DAILY)
                .priority(request.priority() != null ? request.priority() : PriorityLevel.MEDIUM)
                .estimatedDuration(request.estimatedDuration())
                .dueMoment(request.dueMoment())
                .build();
        Activity savedActivity = repository.save(activity);

        log.info("Plantilla de la Actividad '{}' creada correctamente.", request.title());

        return savedActivity;
    }

    @Override
    public List<Activity> findAllTemplates() {
        return repository.findByIsTemplate(true);
    }

    @Override
    public List<Activity> findTemplatesByCategory(CategoryType category) {
        return repository.findByCategoryAndIsTemplate(category, true);
    }

    @Override
    public boolean deleteTemplate(Long id) {
        Activity activity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plantilla"));

        if (!activity.getIsTemplate()) throw new InvalidDataException("El recurso solicitado no es una plantilla.");

        log.info("Plantilla eliminada correctamente.");

        return repository.deleteById(id);
    }

    @Override
    public Activity cloneTemplate(Long id, Long userId) {
        Activity activity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plantilla"));

        if (!activity.getIsTemplate()) throw new InvalidDataException("El recurso solicitado no es una plantilla.");

        User user = userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario"));

        Activity clonedActivity = Activity.builder()
                .id(null)
                .title(activity.getTitle())
                .description(activity.getDescription())
                .isTemplate(false)
                .user(user)
                .category(activity.getCategory())
                .frequency(activity.getFrequency())
                .status(ActivityStatus.PENDING)
                .priority(activity.getPriority())
                .createdAt(Instant.now())
                .estimatedDuration(activity.getEstimatedDuration())
                .dueMoment(activity.getDueMoment())
                .build();
        Activity savedActivity = repository.save(clonedActivity);

        log.info("Actividad personalizada '{}' creada correctamente.", savedActivity.getTitle());

        return savedActivity;
    }

    @Override
    public Activity createActivity(CreateActivityRequest request, Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario"));

        if (repository.existsByTitleAndUserIdAndIsTemplate(request.title(), userId, false))
            throw new NameAlreadyExistsException();

        Activity activity = Activity.builder()
                .title(request.title())
                .description(request.description())
                .isTemplate(false)
                .user(user)
                .category(request.category() != null ? request.category() : CategoryType.OTHER)
                .frequency(request.frequency() != null ? request.frequency() : FrequencyType.DAILY)
                .status(ActivityStatus.PENDING)
                .priority(request.priority() != null ? request.priority() : PriorityLevel.MEDIUM)
                .createdAt(Instant.now())
                .estimatedDuration(request.estimatedDuration())
                .dueMoment(request.dueMoment())
                .build();
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
        Activity activity = findActivityById(id, userId);

        Activity updatedActivity = Activity.builder()
                .id(activity.getId())
                .title(request.title() != null ? request.title() : activity.getTitle())
                .description(request.description() != null ? request.description() : activity.getDescription())
                .isTemplate(activity.getIsTemplate())
                .user(activity.getUser())
                .category(request.category() != null ? request.category() : activity.getCategory())
                .frequency(request.frequency() != null ? request.frequency() : activity.getFrequency())
                .status(request.status() != null ? request.status() : activity.getStatus())
                .priority(request.priority() != null ? request.priority() : activity.getPriority())
                .createdAt(activity.getCreatedAt())
                .estimatedDuration(request.estimatedDuration() != null ? request.estimatedDuration() : activity.getEstimatedDuration())
                .dueMoment(request.dueMoment() != null ? request.dueMoment() : activity.getDueMoment())
                .completedAt(activity.getCompletedAt())
                .build();

        repository.save(updatedActivity);
        log.info("Actividad actualizada correctamente.");

        return updatedActivity;
    }

    @Override
    public Activity completeActivity(Long id, Long userId) {
        Activity activity = findActivityById(id, userId);

        Activity completed = Activity.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .description(activity.getDescription())
                .isTemplate(activity.getIsTemplate())
                .user(activity.getUser())
                .category(activity.getCategory())
                .frequency(activity.getFrequency())
                .status(ActivityStatus.COMPLETED)
                .priority(activity.getPriority())
                .createdAt(activity.getCreatedAt())
                .estimatedDuration(activity.getEstimatedDuration())
                .dueMoment(activity.getDueMoment())
                .completedAt(Instant.now())
                .build();

        repository.save(completed);
        log.info("Actividad completada exitosamente. Felicidades ¡!");

        return completed;
    }

    @Override
    public Activity uncompleteActivity(Long id, Long userId) {
        Activity activity = findActivityById(id, userId);

        Activity notCompleted = Activity.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .description(activity.getDescription())
                .isTemplate(activity.getIsTemplate())
                .user(activity.getUser())
                .category(activity.getCategory())
                .frequency(activity.getFrequency())
                .status(ActivityStatus.PENDING)
                .priority(activity.getPriority())
                .createdAt(activity.getCreatedAt())
                .estimatedDuration(activity.getEstimatedDuration())
                .dueMoment(activity.getDueMoment())
                .completedAt(null)
                .build();

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
