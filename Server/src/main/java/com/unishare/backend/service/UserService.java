package com.unishare.backend.service;

import com.unishare.backend.DTO.UserRequest;
import com.unishare.backend.DTO.UserResponse;
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

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getProfilePictureUrl()))
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getProfilePictureUrl());
    }


    public UserResponse updateUser(Integer id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        user.setFullName(userRequest.getFullName());
        user.setProfilePictureUrl(userRequest.getProfilePictureUrl());
        // Update other user properties as needed

        user = userRepository.save(user);

        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getProfilePictureUrl());
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        // You may want to handle cascading deletes or other related actions here
        userRepository.delete(user);
    }

    // Add more service methods here as needed
}
