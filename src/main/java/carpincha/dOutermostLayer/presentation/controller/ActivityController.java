package carpincha.dOutermostLayer.presentation.controller;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import carpincha.cApplicationService.dto.activity.request.UpdateActivityRequest;
import carpincha.cApplicationService.dto.activity.response.ActivityResponse;
import carpincha.cApplicationService.mapper.response.ActivityResponseMapper;
import carpincha.cApplicationService.service.ActivityService;
import carpincha.cApplicationService.service.TemplateService;
import carpincha.dOutermostLayer.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
@Tag(name = "Activities.", description = "Endpoints for managing user activities and templates.")
@SecurityRequirement(name = "bearerAuth")
public class ActivityController {


    private final ActivityService service;
    private final TemplateService templateService;
    private final ActivityResponseMapper mapper;

    @GetMapping("/templates")
    @Operation(summary = "Get available activity templates.",
            description = "Retrieves all activity templates, optionally filtered by category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Templates retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Not authenticated.")
    })
    public ResponseEntity<List<ActivityResponse>> getAvailableTemplates(
            @Parameter(description = "Filter templates by category.")
            @RequestParam(required = false) CategoryType category) {

        List<Activity> activities = category != null
                ? templateService.findTemplatesByCategory(category)
                : templateService.findAllTemplates();

        return ResponseEntity.ok(mapper.toResponseList(activities));
    }

    @GetMapping("/templates/{id}")
    @Operation(summary = "Get a specific template.",
            description = "Retrieves a single activity template by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Template retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Template not found."),
            @ApiResponse(responseCode = "401", description = "Not authenticated.")
    })
    public ResponseEntity<ActivityResponse> getAvailableTemplate(
            @Parameter(description = "Template ID.") @PathVariable Long id) {
        Activity activity = templateService.findTemplateById(id);
        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @PostMapping("/clone/{id}")
    @Operation(summary = "Clone a template.",
            description = "Creates a new activity for the user based on a template.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Activity cloned successfully."),
            @ApiResponse(responseCode = "404", description = "Template not found."),
            @ApiResponse(responseCode = "401", description = "Not authenticated.")
    })
    public ResponseEntity<ActivityResponse> cloneTemplate(
            @Parameter(description = "Template ID to clone.") @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = service.cloneTemplate(id, userId);

        return new ResponseEntity<>(mapper.toResponse(activity), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get activity by ID.",
            description = "Retrieves a specific activity belonging to the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Activity not found."),
            @ApiResponse(responseCode = "401", description = "Not authenticated.")
    })
    public ResponseEntity<ActivityResponse> getActivityById(
            @Parameter(description = "Activity ID.") @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = service.findActivityById(id, userId);

        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @GetMapping
    @Operation(summary = "Get user activities.",
            description = "Retrieves all activities for the authenticated user, with optional filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activities retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Not authenticated.")
    })
    public ResponseEntity<List<ActivityResponse>> getActivities(
            @Parameter(description = "Filter by activity status.") @RequestParam(required = false) ActivityStatus status,
            @Parameter(description = "Filter by category.") @RequestParam(required = false) CategoryType category,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        List<Activity> activities;

        if (category != null) {
            activities = service.findActivitiesByUserIdAndCategory(userId, category);
        } else if (status != null) {
            activities = service.findActivitiesByUserIdAndStatus(userId, status);
        } else {
            activities = service.findActivitiesByUserId(userId);
        }
        return ResponseEntity.ok(mapper.toResponseList(activities));
    }

    @PostMapping
    @Operation(summary = "Create a new activity.",
            description = "Creates a new activity for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Activity created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "401", description = "Not authenticated.")
    })
    public ResponseEntity<ActivityResponse> createActivity(
            @RequestBody @Valid CreateActivityRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = service.createActivity(request, userId);

        return new ResponseEntity<>(mapper.toResponse(activity), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an activity.",
            description = "Updates an existing activity belonging to the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity updated successfully."),
            @ApiResponse(responseCode = "404", description = "Activity not found."),
            @ApiResponse(responseCode = "401", description = "Not authenticated.")
    })
    public ResponseEntity<ActivityResponse> updateActivity(
            @Parameter(description = "Activity ID.") @PathVariable Long id,
            @RequestBody @Valid UpdateActivityRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = service.updateActivity(id, request, userId);

        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @PostMapping("/{id}/complete")
    @Operation(summary = "Mark activity as complete.",
            description = "Marks an activity as completed.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity marked as complete."),
            @ApiResponse(responseCode = "404", description = "Activity not found."),
            @ApiResponse(responseCode = "401", description = "Not authenticated.")
    })
    public ResponseEntity<ActivityResponse> completeActivity(
            @Parameter(description = "Activity ID.") @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = service.completeActivity(id, userId);

        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @PostMapping("/{id}/uncomplete")
    @Operation(summary = "Mark activity as incomplete.",
            description = "Marks a completed activity as incomplete.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity marked as incomplete."),
            @ApiResponse(responseCode = "404", description = "Activity not found."),
            @ApiResponse(responseCode = "401", description = "Not authenticated.")
    })
    public ResponseEntity<ActivityResponse> uncompleteActivity(
            @Parameter(description = "Activity ID") @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = service.uncompleteActivity(id, userId);

        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an activity.",
            description = "Deletes an activity belonging to the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Activity deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Activity not found."),
            @ApiResponse(responseCode = "401", description = "Not authenticated.")
    })
    public ResponseEntity<Void> deleteActivity(
            @Parameter(description = "Activity ID.") @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        service.deleteActivity(id, userId);

        return ResponseEntity.noContent().build();
    }
}