package carpincha.dOutermostLayer.presentation.controller;

import carpincha.aCore.entity.activity.Activity;
import carpincha.cApplicationService.dto.activity.request.CreateActivityRequest;
import carpincha.cApplicationService.dto.activity.response.ActivityResponse;
import carpincha.cApplicationService.mapper.ActivityResponseMapper;
import carpincha.cApplicationService.service.ActivityService;
import carpincha.dOutermostLayer.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityResponseMapper mapper;

    @PostMapping
    public ResponseEntity<ActivityResponse> createActivity(
            @RequestBody @Valid CreateActivityRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.userId();
        Activity activity = activityService.createActivity(request, userId);

        return new ResponseEntity<>(mapper.toResponse(activity), HttpStatus.CREATED);
    }

}
