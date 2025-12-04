package carpincha.dOutermostLayer.presentation.controller;

import carpincha.aCore.entity.user.User;
import carpincha.aCore.valueObject.Role;
import carpincha.cApplicationService.dto.user.response.UserInfoResponse;
import carpincha.cApplicationService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@Tag(name = "User Management.", description = "Admin endpoints to manage system users.")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users.", description = "Retrive a list of all registered uses in the system, for Admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrived successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid."),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required.")
    })
    public ResponseEntity<List<UserInfoResponse>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserInfoResponse> response = users.stream()
                .map(user -> new UserInfoResponse(
                        user.getId(),
                        user.getName(),
                        user.getRole()
                ))
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/role")
    @Operation(summary = "Change user role.", description = "Change the role of a sepecific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role changed successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid role or operation."),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid."),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<UserInfoResponse> changeUserRole(
            @PathVariable Long id,
            @RequestBody @Valid Role role
    ){
        User user = userService.changeUserRole(id, role);
        UserInfoResponse response = new UserInfoResponse(
                user.getId(),
                user.getName(),
                user.getRole()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user.", description = "Permanently delete a user -and all it's activities- from the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully."),
        @ApiResponse(responseCode = "400", description = "Cannot delete the last admin."),
        @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid."),
        @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required."),
        @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
