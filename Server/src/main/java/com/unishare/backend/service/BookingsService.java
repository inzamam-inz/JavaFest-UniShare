package com.unishare.backend.service;

import com.unishare.backend.DTO.Request.BookingRequest;
import com.unishare.backend.DTO.Response.BookingResponse;
import com.unishare.backend.DTO.Response.ProductResponse;
import com.unishare.backend.DTO.Response.UserResponse;
import com.unishare.backend.exceptionHandlers.ProductNotFoundException;
import com.unishare.backend.exceptionHandlers.UserNotFoundException;
import com.unishare.backend.model.Booking;
import com.unishare.backend.model.Product;
import com.unishare.backend.model.User;
import com.unishare.backend.repository.BookingRepository;
import com.unishare.backend.repository.ProductRepository;
import com.unishare.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingsService {

    private final BookingRepository bookingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public BookingsService(BookingRepository bookingRepository, ProductRepository productRepository, UserRepository userRepository, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public BookingResponse getBookingById(Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            return convertToResponse(bookingOptional.get());
        }
        throw new RuntimeException("Booking not found with ID: " + id);
    }

    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setRentFrom(bookingRequest.getRentFrom());
        booking.setRentTo(bookingRequest.getRentTo());
        booking.setConfirmationStatus(bookingRequest.getConfirmationStatus());

        Product product = productRepository.findById(bookingRequest.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + bookingRequest.getProductId()));
        booking.setProduct(product);

        User borrower = userRepository.findById(bookingRequest.getBorrowerId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + bookingRequest.getBorrowerId()));
        booking.setBorrower(borrower);

        booking = bookingRepository.save(booking);
        return convertToResponse(booking);
    }

    public void deleteBooking(Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            bookingRepository.delete(booking);
        } else {
            throw new RuntimeException("Booking not found with ID: " + id);
        }
    }

    private BookingResponse convertToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setRentFrom(booking.getRentFrom());
        response.setRentTo(booking.getRentTo());
        response.setConfirmationStatus(booking.getConfirmationStatus());
        response.setProductResponse(convertProductToResponse(booking.getProduct()));
        response.setBorrower(userService.makeUserResponse(booking.getBorrower()));
        return response;
    }

    private ProductResponse convertProductToResponse(Product product) {
        List<Long> bookingIds = product.getBookings().stream()
                .map(Booking::getId)
                .collect(Collectors.toList());

        ProductResponse response = new ProductResponse();
        response.setProductId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setBasePrice(product.getBasePrice());
        response.setStatus(product.getStatus());
        response.setOwnerId(product.getOwner().getId());
        response.setCategoryId(product.getCategory().getId());
        response.setBookingIds(bookingIds);
        return response;
    }

}
