package carpincha.cApplicationService.service;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.exception.InvalidDataException;
import carpincha.aCore.exception.NameAlreadyExistsException;
import carpincha.aCore.exception.NotFoundException;
import carpincha.aCore.repoInterface.ActivityRepository;
import carpincha.aCore.serviceInterface.TemplatesServiceContract;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.aCore.valueObject.FrequencyType;
import carpincha.aCore.valueObject.PriorityLevel;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateService implements TemplatesServiceContract {

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

}
