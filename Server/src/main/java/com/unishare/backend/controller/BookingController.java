package com.unishare.backend.controller;

import com.unishare.backend.DTO.SpecialResponse.ApiResponse;
import com.unishare.backend.DTO.Request.BookingRequest;
import com.unishare.backend.DTO.Response.BookingResponse;
import com.unishare.backend.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/bookings")
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

    @GetMapping("/self")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookingsByBorrower(@RequestHeader("Authorization") String token) {
        try {
            List<BookingResponse> bookings = bookingsService.getAllBookingsByBorrower(token);
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
    public ResponseEntity<ApiResponse<Boolean>> createBooking(@RequestHeader("Authorization") String token, @RequestBody BookingRequest bookingRequest) {
        try {
            Boolean flag = bookingsService.createBooking(token, bookingRequest);
            return ResponseEntity.ok(new ApiResponse<>(flag, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PutMapping("accept/{id}")
    public ResponseEntity<ApiResponse<Boolean>> acceptBooking(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            Boolean flag = bookingsService.acceptBookingRequest(id, token);
            return ResponseEntity.ok(new ApiResponse<>(flag, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PutMapping("reject/{id}")
    public ResponseEntity<ApiResponse<Boolean>> rejectBooking(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            Boolean flag = bookingsService.rejectBookingRequest(id, token);
            return ResponseEntity.ok(new ApiResponse<>(flag, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PutMapping("lent/{id}")
    public ResponseEntity<ApiResponse<Boolean>> lentBooking(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            Boolean flag = bookingsService.lendProduct(id, token);
            return ResponseEntity.ok(new ApiResponse<>(flag, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PutMapping("cancel/{id}")
    public ResponseEntity<ApiResponse<Boolean>> cancelBooking(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            Boolean flag = bookingsService.cancelBooking(id, token);
            return ResponseEntity.ok(new ApiResponse<>(flag, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PutMapping("complete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> completeBooking(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            Boolean flag = bookingsService.completeBooking(id, token);
            return ResponseEntity.ok(new ApiResponse<>(flag, null));
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
