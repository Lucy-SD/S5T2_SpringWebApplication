package carpincha.dOutermostLayer.presentation.controller;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.cApplicationService.dto.activity.response.ActivityResponse;
import carpincha.cApplicationService.mapper.response.ActivityResponseMapper;
import carpincha.cApplicationService.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/activities")
@RequiredArgsConstructor
@Tag(name = "Admin Activity Management.", description = "Admin endpoints for managing users activities in the system.")
@SecurityRequirement(name = "bearerAuth")
public class AdminActivityController {

    private final ActivityService service;
    private final ActivityResponseMapper mapper;

    @GetMapping
    @Operation(summary = "Admin: Get all activities.",
            description = "Retrieve all activities with optional filters by status and category." +
                    "Admin can see all users activities regardless of visibility."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activities retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid."),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required.")
    })
    public ResponseEntity<List<ActivityResponse>> getAllActivities(
            @RequestParam(required = false) ActivityStatus status,
            @RequestParam(required = false)CategoryType category) {
        List<Activity> activities = service.findAllActivities(status, category);
        return ResponseEntity.ok(mapper.toResponseList(activities));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Admin: Get activity by ID.",
            description = "Retrieve a specific activity by ID. Admin can view any activity regardless of visibility."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid."),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required."),
            @ApiResponse(responseCode = "404", description = "Activity not found.")
    })
    public ResponseEntity<ActivityResponse> getActivity(@PathVariable Long id) {
        Activity activity = service.findActivityByIdAdmin(id);
        return ResponseEntity.ok(mapper.toResponse(activity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Admin: Delete activity.",
            description = "Delete an activity by ID. Only admins can perform this operation."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Activity deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid."),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required."),
            @ApiResponse(responseCode = "404", description = "Activity not found.")
    })
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        service.deleteActivityAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
