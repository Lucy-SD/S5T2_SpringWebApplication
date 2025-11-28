package carpincha.cApplicationService.service;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.exception.InvalidDataException;
import carpincha.aCore.exception.NameAlreadyExistsException;
import carpincha.aCore.exception.NotFoundException;
import carpincha.aCore.repoInterface.ActivityRepository;
import carpincha.aCore.serviceInterface.TemplatesServiceContract;
import carpincha.aCore.valueObject.ActivityParams;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import carpincha.cApplicationService.mapper.request.ActivityRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateService implements TemplatesServiceContract {

    private final ActivityRepository repository;
    private final ActivityRequestMapper mapper;

    @Override
    public Activity createTemplate(CreateActivityRequest request) {

        if (repository.existsByTitleAndUserIdAndIsTemplate(request.title(), null, true))
            throw new NameAlreadyExistsException();

        ActivityParams params = mapper.toParams(request);
        Activity activity = Activity.fromTemplateRequest(params);
        Activity savedActivity = repository.save(activity);

        log.info("Plantilla de la Actividad '{}' creada correctamente.", request.title());

        return savedActivity;
    }

    @Override
    public List<Activity> findAllTemplates() {
        return repository.findByIsTemplate(true);
    }

    @Override
    public Activity findTemplateById(Long id) {
        Activity activity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plantilla"));
        if (!activity.getIsTemplate())
            throw new InvalidDataException("El recurso solicitado no es una plantilla.");
        return activity;
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
