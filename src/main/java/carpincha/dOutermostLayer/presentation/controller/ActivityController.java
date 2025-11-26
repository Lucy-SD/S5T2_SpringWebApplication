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

    private final ActivityService activityService;
    private final ActivityResponseMapper mapper;

    @PostMapping("/clone/{id}")
    public ResponseEntity<ActivityResponse> cloneTemplate(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = activityService.cloneTemplate(id, userId);

        return new ResponseEntity<>(mapper.toResponse(activity), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<ActivityResponse> createActivity(
            @RequestBody @Valid CreateActivityRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = activityService.createActivity(request, userId);

        return new ResponseEntity<>(mapper.toResponse(activity), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivityById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = activityService.findActivityById(id, userId);

        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ActivityResponse> updateActivity(
            @PathVariable Long id,
            @RequestBody @Valid UpdateActivityRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = activityService.updateActivity(id, request, userId);

        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<ActivityResponse> completeActivity(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = activityService.completeActivity(id, userId);

        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @PostMapping("/{id}/uncomplete")
    public ResponseEntity<ActivityResponse> uncompleteActivity(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = activityService.uncompleteActivity(id, userId);

        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        activityService.deleteActivity(id, userId);

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
            activities = activityService.findActivitiesByUserIdAndCategory(userId, category);
        } else if (status != null) {
            activities = activityService.findActivitiesByUserIdAndStatus(userId, status);
        } else {
            activities = activityService.findActivitiesByUserId(userId);
        }
        return ResponseEntity.ok(mapper.toResponseList(activities));
    }

}
