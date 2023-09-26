package com.unishare.backend.service;

import com.unishare.backend.DTO.Response.CategoryResponse;
import com.unishare.backend.exceptionHandlers.CategoryNotFoundException;
import com.unishare.backend.model.Category;
import com.unishare.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getCategoryName(), category.getDescription()))
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
        return new CategoryResponse(category.getId(), category.getCategoryName(), category.getDescription());
    }

    public CategoryResponse createCategory(Category category) {
        category = categoryRepository.save(category);
        return new CategoryResponse(category.getId(), category.getCategoryName(), category.getDescription());
    }

    public CategoryResponse updateCategory(Long id, Category updatedCategory) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

        category.setCategoryName(updatedCategory.getCategoryName());
        category.setDescription(updatedCategory.getDescription());

        category = categoryRepository.save(category);
        return new CategoryResponse(category.getId(), category.getCategoryName(), category.getDescription());
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

        categoryRepository.delete(category);
    }
}
