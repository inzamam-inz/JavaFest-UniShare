package com.unishare.backend.controller;

import com.unishare.backend.DTO.ApiResponse.ApiResponse;
import com.unishare.backend.DTO.Request.UniversityRequest;
import com.unishare.backend.DTO.Response.UniversityResponse;
import com.unishare.backend.model.University;
import com.unishare.backend.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/university")
public class UniversityController {

    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UniversityResponse>>> getAllUniversities() {
        try {
            List<UniversityResponse> universityResponses = universityService.getAllUniversities();
            return ResponseEntity.ok(new ApiResponse<>(universityResponses, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UniversityResponse>> getUniversityById(@PathVariable Long id) {
        try {
            UniversityResponse universityResponse = universityService.getUniversityById(id);
            return ResponseEntity.ok(new ApiResponse<>(universityResponse, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<UniversityResponse>> createUniversity(@RequestBody UniversityRequest university) {
        try {
            UniversityResponse createdUniversityResponse = universityService.createUniversity(university.getUniversityName());
            return ResponseEntity.ok(new ApiResponse<>(createdUniversityResponse, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UniversityResponse>> updateUniversity(@PathVariable Long id, @RequestBody UniversityRequest updatedUniversity) {
        try {
            UniversityResponse updatedUniversityResponse = universityService.updateUniversity(id, updatedUniversity.getUniversityName());
            return updatedUniversityResponse != null
                    ? ResponseEntity.ok(new ApiResponse<>(updatedUniversityResponse, null))
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUniversity(@PathVariable Long id) {
        try {
            universityService.deleteUniversity(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>());
        }
    }
}
