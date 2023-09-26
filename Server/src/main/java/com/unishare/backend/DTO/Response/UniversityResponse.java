package com.unishare.backend.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversityResponse {
    private Long id;
    private String universityName;
}
