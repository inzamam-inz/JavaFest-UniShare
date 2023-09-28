package com.unishare.backend.repository;

import com.unishare.backend.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryId(Long categoryId);
    List<Product> findAllByOwnerId(Long categoryId);
    List<Product> findAllByStatus(String status);
    List<Product> findAllByOwnerIdAndCategoryId(Long ownerId, Long categoryId);
    List<Product> findAllByOwnerIdAndStatus(Long ownerId, String status);
    List<Product> findAllByCategoryIdAndStatus(Long categoryId, String status);
    List<Product> findAllByOwnerIdAndStatusAndCategoryId(Long ownerId, String status, Long categoryId);
}
