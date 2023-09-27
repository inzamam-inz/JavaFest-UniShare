package com.unishare.backend.controller;

import com.unishare.backend.DTO.ApiResponse.ApiResponse;
import com.unishare.backend.DTO.Request.BookingRequest;
import com.unishare.backend.DTO.Response.BookingResponse;
import com.unishare.backend.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingsService bookingsService;

    @Autowired
    public BookingController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings() {
        try {
            List<BookingResponse> bookings = bookingsService.getAllBookings();
            return ResponseEntity.ok(new ApiResponse<>(bookings, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(@PathVariable Long id) {
        try {
            BookingResponse booking = bookingsService.getBookingById(id);
            return ResponseEntity.ok(new ApiResponse<>(booking, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(@RequestBody BookingRequest bookingRequest) {
        try {
            BookingResponse createdBooking = bookingsService.createBooking(bookingRequest);
            return ResponseEntity.ok(new ApiResponse<>(createdBooking, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBooking(@PathVariable Long id) {
        try {
            bookingsService.deleteBooking(id);
            return ResponseEntity.ok(new ApiResponse<>(null, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }
}
