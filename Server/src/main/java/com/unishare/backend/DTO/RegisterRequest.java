package com.unishare.backend.DTO;

import com.unishare.backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String idCardUrl;
    private String profilePictureUrl;
    private String fullName;
    private String password;
    private String email;
    private String address;
    private double lat;
    private double lng;
    private Integer university;
    private Role role;
}
