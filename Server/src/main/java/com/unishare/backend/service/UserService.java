package com.unishare.backend.service;

import com.unishare.backend.DTO.Request.UserUpdateRequest;
import com.unishare.backend.DTO.Response.UserResponse;
import com.unishare.backend.exceptionHandlers.ErrorMessageException;
import com.unishare.backend.exceptionHandlers.UserNotFoundException;
import com.unishare.backend.model.User;
import com.unishare.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse makeUserResponse(User user) {
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getProfilePicture(), user.getIsEmailVerified(), user.getIsVerified(), user.getIsBlocked());
    }
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::makeUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ErrorMessageException("User not found with ID: " + id));
        return makeUserResponse(user);
    }

    public UserResponse userProfileUpdate(UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findByEmail(userUpdateRequest.getEmail())
                .orElseThrow(() -> new ErrorMessageException("Haven't any account with this email"));

        user.setFullName(userUpdateRequest.getFullName());
        user.setProfilePicture(userUpdateRequest.getProfilePicture());
        user = userRepository.save(user);

        return makeUserResponse(user);
    }

    public UserResponse userBlockStatusUpdate(Long id, boolean isBlocked) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ErrorMessageException("User not found with ID: " + id));

        user.setIsBlocked(isBlocked);
        user = userRepository.save(user);

        return makeUserResponse(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ErrorMessageException("User not found with ID: " + id));

        userRepository.delete(user);
    }

    // Add more service methods here as needed
}
