package com.unishare.backend.controller;

import com.unishare.backend.DTO.ApiResponse.ApiResponse;
import com.unishare.backend.DTO.Request.UserUpdateRequest;
import com.unishare.backend.DTO.Response.UserResponse;
import com.unishare.backend.exceptionHandlers.ErrorMessageException;
import com.unishare.backend.model.User;
import com.unishare.backend.repository.UserRepository;
import com.unishare.backend.service.JwtService;
import com.unishare.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, JwtService jwtService, UserRepository userRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @GetMapping()
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        try {
            List<UserResponse> users = userService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse<>(users, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        try {
            UserResponse user = userService.getUserById(id);
            return ResponseEntity.ok(new ApiResponse<>(user, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PutMapping("/profile-update")
    public ResponseEntity<ApiResponse<UserResponse>> profileUpdate(@RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            UserResponse updatedUser = userService.userProfileUpdate(userUpdateRequest);
            return ResponseEntity.ok(new ApiResponse<>(updatedUser, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse<>(null, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PutMapping("/block-user/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> blockUser(@PathVariable Long id) {
        try {
            UserResponse updatedUser = userService.userBlockStatusUpdate(id, true);
            return ResponseEntity.ok(new ApiResponse<>(updatedUser, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PutMapping("/unblock-user/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> unBlockUser(@PathVariable Long id) {
        try {
            UserResponse updatedUser = userService.userBlockStatusUpdate(id, false);
            return ResponseEntity.ok(new ApiResponse<>(updatedUser, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMe(@RequestHeader("Authorization") String token) {
        try {
            UserResponse userResponse = userService.getUserResponseByToken(token);
            return ResponseEntity.ok(new ApiResponse<>(userResponse, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }
}
