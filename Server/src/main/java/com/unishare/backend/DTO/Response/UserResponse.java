package com.unishare.backend.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String idCard;
    private String profilePicture;
    private String address;
    private String phoneNumber;
    private Double lat;
    private Double lng;
    private Long university;
    private boolean isEmailVerified;
    private boolean isVerified;
    private boolean isBlocked;
}
