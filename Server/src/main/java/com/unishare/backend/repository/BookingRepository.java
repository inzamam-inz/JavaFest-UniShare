package com.unishare.backend.repository;

import com.unishare.backend.model.Booking;
import com.unishare.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByProductId(Long categoryId);
    List<Booking> findAllByBorrowerId(Long userId);
}
