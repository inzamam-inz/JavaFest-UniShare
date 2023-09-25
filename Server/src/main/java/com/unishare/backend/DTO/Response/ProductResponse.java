package com.unishare.backend.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long productId;
    private String name;
    private String description;
    private Double basePrice;
    private String status;

    private Long ownerId;
    private Long categoryId;
    private List<Long> bookingIds = new ArrayList<>();
}