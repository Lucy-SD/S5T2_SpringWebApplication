package carpincha.dOutermostLayer.presentation.controller;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import carpincha.cApplicationService.dto.activity.response.ActivityResponse;
import carpincha.cApplicationService.mapper.response.ActivityResponseMapper;
import carpincha.cApplicationService.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/templates")
@RequiredArgsConstructor
@Tag(name = "Template Managment.", description = "Admin endpoints for managing activity templates.")
@SecurityRequirement(name = "bearerAuth")
public class TemplateController {

    private final TemplateService service;
    private final ActivityResponseMapper mapper;

    @PostMapping
    @Operation(summary = "Create activity template.",
            description = "Create a new activity template that can be used to generate activities."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Template created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid."),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required.")
    })
    public ResponseEntity<ActivityResponse> createTemplate(
            @RequestBody @Valid CreateActivityRequest createActivityRequest) {

        Activity template = service.createTemplate(createActivityRequest);
        return new ResponseEntity<>(mapper.toResponse(template), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all templates.",
            description = "Retrieve all activity templates, optionally filtered by category."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Templates retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid."),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required.")
    })
    public ResponseEntity<List<ActivityResponse>> getTemplates(
            @RequestParam(required = false) CategoryType category) {

        List<Activity> activities = category != null
                ? service.findTemplatesByCategory(category)
                : service.findAllTemplates();

        return ResponseEntity.ok(mapper.toResponseList(activities));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete template.",
            description = "Delete an activity template by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Template deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid."),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required."),
            @ApiResponse(responseCode = "404", description = "Template not found.")
    })
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        service.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }
}
