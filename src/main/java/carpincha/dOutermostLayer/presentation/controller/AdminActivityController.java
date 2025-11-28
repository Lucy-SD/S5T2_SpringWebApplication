package carpincha.dOutermostLayer.presentation.controller;

import carpincha.aCore.entity.activity.Activity;
import carpincha.aCore.exception.InvalidDataException;
import carpincha.aCore.valueObject.ActivityStatus;
import carpincha.aCore.valueObject.CategoryType;
import carpincha.cApplicationService.dto.activity.response.ActivityResponse;
import carpincha.cApplicationService.mapper.response.ActivityResponseMapper;
import carpincha.cApplicationService.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/activities")
@RequiredArgsConstructor
public class AdminActivityController {

    private final ActivityService service;
    private final ActivityResponseMapper mapper;

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivities(
            @RequestParam(required = false) ActivityStatus status,
            @RequestParam(required = false)CategoryType category) {
        List<Activity> activities = service.findAllActivities(status, category);
        return ResponseEntity.ok(mapper.toResponseList(activities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivity(@PathVariable Long id) {
        Activity activity = service.findActivityByIdAdmin(id);
        return ResponseEntity.ok(mapper.toResponse(activity));
    }
}
