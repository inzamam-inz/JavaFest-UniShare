package com.unishare.backend.controller;

import com.unishare.backend.DTO.ApiResponse.ApiResponse;
import com.unishare.backend.DTO.Request.ProductRequest;
import com.unishare.backend.DTO.Response.ProductResponse;
import com.unishare.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        try {
            List<ProductResponse> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse<>(products, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        try {
            ProductResponse product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse<>(product, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest product) {
        try {
            ProductResponse createdProduct = productService.createProduct(product);
            return ResponseEntity.ok(new ApiResponse<>(createdProduct, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<ProductResponse>> createProductWithImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("ownerId") Long ownerId
    ) {
        try {
            ProductResponse createdProduct = productService.createProductWithImage(image, name, description, price, categoryId, ownerId);
            return ResponseEntity.ok(new ApiResponse<>(createdProduct, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }
//    ) {
//        try {
//            List<ProductResponse> products = productService.searchProducts(product);
//            return ResponseEntity.ok(new ApiResponse<>(products, null));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
//        }
//    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        ProductResponse updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
