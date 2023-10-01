package com.unishare.backend.service;

import com.unishare.backend.DTO.Request.UserUpdateRequest;
import com.unishare.backend.DTO.Response.UserResponse;
import com.unishare.backend.DTO.SpecialResponse.PageResponse;
import com.unishare.backend.exceptionHandlers.ErrorMessageException;
import com.unishare.backend.exceptionHandlers.UserNotFoundException;
import com.unishare.backend.model.User;
import com.unishare.backend.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public UserResponse makeUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getIdCard(),
                user.getProfilePicture(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getLat(),
                user.getLng(),
                user.getUniversity().getId(),
                user.getIsEmailVerified(),
                user.getIsVerified(),
                user.getIsBlocked()
        );
    }

    @Cacheable("user-all")
    public PageResponse<List<UserResponse>> getAllUsers(int page, int size) {
        Page<User> userPage = userRepository.getUsersPage(PageRequest.of(page, size));

        PageResponse<List<UserResponse>> pageResponse = new PageResponse<>();
        List<UserResponse> users = userPage.stream()
                .map(this::makeUserResponse)
                .collect(Collectors.toList());
        pageResponse.setData(users);
        pageResponse.setTotalPages(userPage.getTotalPages());
        pageResponse.setTotalElements(userPage.getTotalElements());
        pageResponse.setCurrentPage(userPage.getNumber());
        pageResponse.setCurrentElements(userPage.getNumberOfElements());
        return pageResponse;
    }


//    public List<UserResponse> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        return users.stream()
//                .map(this::makeUserResponse)
//                .collect(Collectors.toList());
//    }

    @Cacheable("user-#id")
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ErrorMessageException("User not found with ID: " + id));
        return makeUserResponse(user);
    }

    @CacheEvict(value = {"user-all", "user-#id"}, allEntries = true)
    public UserResponse userProfileUpdate(UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findByEmail(userUpdateRequest.getEmail())
                .orElseThrow(() -> new ErrorMessageException("Haven't any account with this email"));

        user.setFullName(userUpdateRequest.getFullName());
        user.setProfilePicture(userUpdateRequest.getProfilePicture());
        user = userRepository.save(user);

        return makeUserResponse(user);
    }

    @CacheEvict(value = {"user-#id", "user-all"}, allEntries = true)
    public UserResponse userBlockStatusUpdate(Long id, boolean isBlocked) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ErrorMessageException("User not found with ID: " + id));

        user.setIsBlocked(isBlocked);
        user = userRepository.save(user);

        return makeUserResponse(user);
    }

    @CacheEvict(value = {"user-#id", "user-all"}, allEntries = true)
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ErrorMessageException("User not found with ID: " + id));

        userRepository.delete(user);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorMessageException("User not found with email: " + email));
        return makeUserResponse(user);
    }

    public UserResponse getUserResponseByToken(String token) {
        String email = jwtService.extractEmailFromBearerToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorMessageException("User not found with token."));
        return makeUserResponse(user);
    }

    public User getUserByToken(String token) {
        String email = jwtService.extractEmailFromBearerToken(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorMessageException("User not found with token."));
    }

    public Long getUserIdFromToken(String token) {
        String email = jwtService.extractEmailFromBearerToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorMessageException("User not found with token."));
        return user.getId();
    }

    // Add more service methods here as needed
}
