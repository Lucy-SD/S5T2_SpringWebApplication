package carpincha.dOutermostLayer.presentation.controller;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.exception.InvalidDataException;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import carpincha.cApplicationService.dto.activity.request.UpdateActivityRequest;
import carpincha.cApplicationService.dto.activity.response.ActivityResponse;
import carpincha.cApplicationService.mapper.ActivityResponseMapper;
import carpincha.cApplicationService.service.ActivityService;
import carpincha.cApplicationService.service.TemplateService;
import carpincha.dOutermostLayer.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService service;
    private final TemplateService templateService;
    private final ActivityResponseMapper mapper;

    @GetMapping("/templates")
    public ResponseEntity<List<ActivityResponse>> getAvailableTemplates(
            @RequestParam(required = false) @Valid CategoryType category) {

        List<Activity> activities = category != null
                ? templateService.findTemplatesByCategory(category)
                : templateService.findAllTemplates();

        return ResponseEntity.ok(mapper.toResponseList(activities));
    }

    @GetMapping("/templates/{id}")
    public ResponseEntity<ActivityResponse> getAvailableTemplate(@PathVariable Long id) {
        Activity activity = templateService.findTemplateById(id);
        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @PostMapping("/clone/{id}")
    public ResponseEntity<ActivityResponse> cloneTemplate(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = service.cloneTemplate(id, userId);

        return new ResponseEntity<>(mapper.toResponse(activity), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<ActivityResponse> createActivity(
            @RequestBody @Valid CreateActivityRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = service.createActivity(request, userId);

        return new ResponseEntity<>(mapper.toResponse(activity), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivityById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = service.findActivityById(id, userId);

        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ActivityResponse> updateActivity(
            @PathVariable Long id,
            @RequestBody @Valid UpdateActivityRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = service.updateActivity(id, request, userId);

        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<ActivityResponse> completeActivity(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = service.completeActivity(id, userId);

        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @PostMapping("/{id}/uncomplete")
    public ResponseEntity<ActivityResponse> uncompleteActivity(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = service.uncompleteActivity(id, userId);

        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        service.deleteActivity(id, userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getActivities(
            @RequestParam(required = false) ActivityStatus status,
            @RequestParam(required = false) CategoryType category,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        List<Activity> activities;

        if (status != null && category != null) {
            throw new InvalidDataException("Seleccione un solo filro.");
        }

        if (category != null) {
            activities = service.findActivitiesByUserIdAndCategory(userId, category);
        } else if (status != null) {
            activities = service.findActivitiesByUserIdAndStatus(userId, status);
        } else {
            activities = service.findActivitiesByUserId(userId);
        }
        return ResponseEntity.ok(mapper.toResponseList(activities));
    }

}
