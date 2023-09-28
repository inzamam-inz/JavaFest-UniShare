package com.unishare.backend.service;

import com.unishare.backend.DTO.Request.ProductRequest;
import com.unishare.backend.DTO.Response.ProductResponse;
import com.unishare.backend.exceptionHandlers.CategoryNotFoundException;
import com.unishare.backend.exceptionHandlers.ErrorMessageException;
import com.unishare.backend.exceptionHandlers.ProductNotFoundException;
import com.unishare.backend.exceptionHandlers.UserNotFoundException;
import com.unishare.backend.model.Booking;
import com.unishare.backend.model.Category;
import com.unishare.backend.model.Product;
import com.unishare.backend.model.User;
import com.unishare.backend.repository.BookingRepository;
import com.unishare.backend.repository.CategoryRepository;
import com.unishare.backend.repository.ProductRepository;
import com.unishare.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryImageService cloudinaryImageService;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, UserRepository userRepository,
                          CategoryRepository categoryRepository, CloudinaryImageService cloudinaryImageService, UserService userService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.cloudinaryImageService = cloudinaryImageService;
        this.userService = userService;
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return convertToResponse(productOptional.get());
        }
        throw new ProductNotFoundException("Product not found with ID: " + id);
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setBasePrice(productRequest.getBasePrice());
        product.setStatus(productRequest.getStatus());

        User owner = userRepository.findById(productRequest.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + productRequest.getOwnerId()));
        product.setOwner(owner);

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + productRequest.getCategoryId()));
        product.setCategory(category);

        product = productRepository.save(product);
        return convertToResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            product.setBasePrice(productRequest.getBasePrice());
            product.setStatus(productRequest.getStatus());

            User owner = userRepository.findById(productRequest.getOwnerId())
                    .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + productRequest.getOwnerId()));
            product.setOwner(owner);

            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + productRequest.getCategoryId()));
            product.setCategory(category);

            product = productRepository.save(product);
            return convertToResponse(product);
        }
        throw new ProductNotFoundException("Product not found with ID: " + id);
    }

    public void deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            // You may want to handle cascading deletes or other related actions here
            productRepository.delete(product);
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
    }

    private ProductResponse convertToResponse(Product product) {
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
        response.setImage(product.getImage());
        response.setPerDayPrice(product.getPerDayPrice());

        return response;
    }

    public ProductResponse createProductWithImage(MultipartFile image, String name, String description, Double price, Double perDay, Long categoryId, String token) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ErrorMessageException("Category not found with ID: " + categoryId));
        User owner = userService.getUserByToken(token);

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setBasePrice(price);
        product.setPerDayPrice(perDay);
        product.setStatus("Available");
        product.setOwner(owner);
        product.setCategory(category);

        String imageUrl = cloudinaryImageService.getUploadedImageUrl(image);
        product.setImage(imageUrl);

        product = productRepository.save(product);
        return convertToResponse(product);
    }
}
