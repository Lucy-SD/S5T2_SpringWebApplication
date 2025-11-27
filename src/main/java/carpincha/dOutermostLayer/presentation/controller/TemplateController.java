package carpincha.dOutermostLayer.presentation.controller;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import carpincha.cApplicationService.dto.activity.response.ActivityResponse;
import carpincha.cApplicationService.mapper.ActivityResponseMapper;
import carpincha.cApplicationService.service.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService service;
    private final ActivityResponseMapper mapper;

    @PostMapping
    public ResponseEntity<ActivityResponse> createTemplate(
            @RequestBody @Valid CreateActivityRequest createActivityRequest) {

        Activity template = service.createTemplate(createActivityRequest);
        return new ResponseEntity<>(mapper.toResponse(template), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getTemplates(
            @RequestParam(required = false) CategoryType category) {

        List<Activity> activities = category != null
                ? service.findTemplatesByCategory(category)
                : service.findAllTemplates();

        return ResponseEntity.ok(mapper.toResponseList(activities));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        service.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }
}
