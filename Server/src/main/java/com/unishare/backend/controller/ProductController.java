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

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest product) {
        try {
            ProductResponse createdProduct = productService.createProduct(product);
            return ResponseEntity.ok(new ApiResponse<>(createdProduct, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ProductResponse>> createProductWithImage(
            @RequestHeader("Authorization") String token,
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("perDay") Double perDay,
            @RequestParam("categoryId") Long categoryId
    ) {
        try {
            ProductResponse createdProduct = productService.createProductWithImage(image, name, description, price, perDay, categoryId, token);
            return ResponseEntity.ok(new ApiResponse<>(createdProduct, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        try {
            ProductResponse updatedProduct = productService.updateProduct(id, productRequest);
            return updatedProduct != null
                    ? ResponseEntity.ok(new ApiResponse<>(updatedProduct, null))
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>());
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByCategoryId(@PathVariable Long id) {
        try {
            List<ProductResponse> products = productService.getProductsByCategoryId(id);
            return ResponseEntity.ok(new ApiResponse<>(products, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByOwnerId(@PathVariable Long id) {
        try {
            List<ProductResponse> products = productService.getProductsByOwnerId(id);
            return ResponseEntity.ok(new ApiResponse<>(products, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByStatus(@PathVariable String status) {
        try {
            List<ProductResponse> products = productService.getProductsByStatus(status);
            return ResponseEntity.ok(new ApiResponse<>(products, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @GetMapping("/owner/{id}/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByOwnerIdAndCategoryId(@PathVariable Long id, @PathVariable Long categoryId) {
        try {
            List<ProductResponse> products = productService.getProductsByOwnerIdAndCategoryId(id, categoryId);
            return ResponseEntity.ok(new ApiResponse<>(products, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @GetMapping("/owner/{id}/status/{status}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByOwnerIdAndStatus(@PathVariable Long id, @PathVariable String status) {
        try {
            List<ProductResponse> products = productService.getProductsByOwnerIdAndStatus(id, status);
            return ResponseEntity.ok(new ApiResponse<>(products, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @GetMapping("/owner/{id}/category/{categoryId}/status/{status}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByOwnerIdAndStatusAndCategoryId(@PathVariable Long id, @PathVariable String status, @PathVariable Long categoryId) {
        try {
            List<ProductResponse> products = productService.getProductsByOwnerIdAndStatusAndCategoryId(id, status, categoryId);
            return ResponseEntity.ok(new ApiResponse<>(products, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }

    @GetMapping("/category/{categoryId}/status/{status}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByCategoryIdAndStatus(@PathVariable Long categoryId, @PathVariable String status) {
        try {
            List<ProductResponse> products = productService.getProductsByCategoryIdAndStatus(categoryId, status);
            return ResponseEntity.ok(new ApiResponse<>(products, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, e.getMessage()));
        }
    }
}
