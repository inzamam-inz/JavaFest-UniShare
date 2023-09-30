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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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

//    public ProductResponse createProduct(ProductRequest productRequest) {
//        Product product = new Product();
//        product.setName(productRequest.getName());
//        product.setDescription(productRequest.getDescription());
//        product.setBasePrice(productRequest.getBasePrice());
//        product.setStatus(productRequest.getStatus());
//
//        User owner = userRepository.findById(productRequest.getOwnerId())
//                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + productRequest.getOwnerId()));
//        product.setOwner(owner);
//
//        Category category = categoryRepository.findById(productRequest.getCategoryId())
//                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + productRequest.getCategoryId()));
//        product.setCategory(category);
//
//        product = productRepository.save(product);
//        return convertToResponse(product);
//    }

//    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
//        Optional<Product> productOptional = productRepository.findById(id);
//        if (productOptional.isPresent()) {
//            Product product = productOptional.get();
//            product.setName(productRequest.getName());
//            product.setDescription(productRequest.getDescription());
//            product.setBasePrice(productRequest.getBasePrice());
//            product.setPerDayPrice(productRequest.getPerDayPrice());
//            product.setStatus(productRequest.getStatus());
//
//            User owner = userRepository.findById(productRequest.getOwnerId())
//                    .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + productRequest.getOwnerId()));
//            product.setOwner(owner);
//
//            Category category = categoryRepository.findById(productRequest.getCategoryId())
//                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + productRequest.getCategoryId()));
//            product.setCategory(category);
//
//            product = productRepository.save(product);
//            return convertToResponse(product);
//        }
//        throw new ProductNotFoundException("Product not found with ID: " + id);
//    }

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

    private Double getTotalPrice(Product product, int dayCount) {
        return product.getBasePrice() + product.getPerDayPrice() * dayCount;
    }

    private Double getTotalPrice(Product product) {
        return product.getBasePrice() + product.getPerDayPrice();
    }

    private Double getRating(Product product) {
        List<Booking> bookings = product.getBookings();
        Double totalRating = 0.0;
        for (Booking booking : bookings) {
            if (Objects.nonNull(booking.getReview()) && Objects.nonNull(booking.getReview().getRating())) {
                totalRating += booking.getReview().getRating();
            }
        }
        return totalRating / bookings.size();
    }

    private Integer getRatingCount(Product product) {
        List<Booking> bookings = product.getBookings();
        Integer ratingCount = 0;
        for (Booking booking : bookings) {
            if (Objects.nonNull(booking.getReview()) && Objects.nonNull(booking.getReview().getRating())) {
                ratingCount++;
            }
        }
        return ratingCount;
    }

    private ProductResponse convertToResponseHelp(Product product) {
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
        response.setImage1(product.getImage1());
        response.setImage2(product.getImage2());
        response.setImage3(product.getImage3());
        response.setPerDayPrice(product.getPerDayPrice());

        return response;
    }


    private ProductResponse convertToResponse(Product product) {
        List<Long> bookingIds = product.getBookings().stream()
                .map(Booking::getId)
                .collect(Collectors.toList());

        ProductResponse response = convertToResponseHelp(product);
        response.setRating(getRating(product));
        response.setRatingCount(getRatingCount(product));
        response.setTotalPrice(getTotalPrice(product));

        return response;
    }

    private ProductResponse convertToResponse(Product product, int dayCount) {
        List<Long> bookingIds = product.getBookings().stream()
                .map(Booking::getId)
                .collect(Collectors.toList());

        ProductResponse response = convertToResponseHelp(product);
        response.setRating(getRating(product));
        response.setRatingCount(getRatingCount(product));
        response.setTotalPrice(getTotalPrice(product, dayCount));

        return response;
    }

    public ProductResponse createProductWithImage(List<MultipartFile> images, String name, String description, Double price, Double perDayPrice, Long categoryId, String token) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ErrorMessageException("Category not found with ID: " + categoryId));
        User owner = userService.getUserByToken(token);

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setBasePrice(price);
        product.setPerDayPrice(perDayPrice);
        product.setStatus("Available");
        product.setOwner(owner);
        product.setCategory(category);

        String imageUrl1 = cloudinaryImageService.getUploadedImageUrl(images.get(0));
        product.setImage1(imageUrl1);

        if (images.size() > 1) {
            String imageUrl2 = cloudinaryImageService.getUploadedImageUrl(images.get(1));
            product.setImage2(imageUrl2);
        }

        if (images.size() > 2) {
            String imageUrl3 = cloudinaryImageService.getUploadedImageUrl(images.get(2));
            product.setImage3(imageUrl3);
        }

        product = productRepository.save(product);
        return convertToResponse(product);
    }

    public List<ProductResponse> getProductsByCategoryId(Long id) {
        List<Product> products = productRepository.findAllByCategoryId(id);
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByOwnerId(Long id) {
        List<Product> products = productRepository.findAllByOwnerId(id);
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByOwnerIdAndStatus(Long id, String status) {
        List<Product> products = productRepository.findAllByOwnerIdAndStatus(id, status);
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByOwnerIdAndStatusAndCategoryId(Long id, String status, Long categoryId) {
        List<Product> products = productRepository.findAllByOwnerIdAndStatusAndCategoryId(id, status, categoryId);
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByOwnerIdAndCategoryId(Long id, Long categoryId) {
        List<Product> products = productRepository.findAllByOwnerIdAndCategoryId(id, categoryId);
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByStatus(String status) {
        List<Product> products = productRepository.findAllByStatus(status);
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByCategoryIdAndStatus(Long categoryId, String status) {
        List<Product> products = productRepository.findAllByCategoryIdAndStatus(categoryId, status);
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByCategoryIdAndStatusWithDayCount(Long categoryId, String status, int dayCount) {
        List<Product> products = productRepository.findAllByCategoryIdAndStatus(categoryId, status);
        return products.stream()
                .map(product -> convertToResponse(product, dayCount))
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByCategoryIdAndStatusAndDayCountAndPriceAsc(Long categoryId, String status, int dayCount) {
        List<ProductResponse> products = getProductsByCategoryIdAndStatusWithDayCount(categoryId, status, dayCount);
        return products.stream()
                .sorted(Comparator.comparingDouble(ProductResponse::getTotalPrice))
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByCategoryIdAndStatusAndDayCountAndPriceDesc(Long categoryId, String status, int dayCount) {
        List<ProductResponse> products = getProductsByCategoryIdAndStatusWithDayCount(categoryId, status, dayCount);
        return products.stream()
                .sorted(Comparator.comparingDouble(ProductResponse::getTotalPrice).reversed())
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByCategoryIdAndStatusAndDayCountAndRatingAsc(Long categoryId, String status, int dayCount) {
        List<ProductResponse> products = getProductsByCategoryIdAndStatusWithDayCount(categoryId, status, dayCount);
        return products.stream()
                .sorted(Comparator.comparingDouble(ProductResponse::getRating))
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByCategoryIdAndStatusAndDayCountAndRatingDesc(Long categoryId, String status, int dayCount) {
        List<ProductResponse> products = getProductsByCategoryIdAndStatusWithDayCount(categoryId, status, dayCount);
        return products.stream()
                .sorted(Comparator.comparingDouble(ProductResponse::getRating).reversed())
                .collect(Collectors.toList());
    }
}
