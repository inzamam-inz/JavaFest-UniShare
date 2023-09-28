package com.unishare.backend.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long id;
    private Date rentFrom;
    private Date rentTo;
    private String confirmationStatus;
    private ProductResponse productResponse;
    private UserResponse borrower;
}