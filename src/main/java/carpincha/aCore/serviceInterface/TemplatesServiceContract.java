package carpincha.aCore.serviceInterface;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;

import java.util.List;

public interface TemplatesServiceContract {

    Activity createTemplate(CreateActivityRequest request);
    List<Activity> findAllTemplates();
    List<Activity> findTemplatesByCategory(CategoryType category);
    boolean deleteTemplate(Long id);

}
