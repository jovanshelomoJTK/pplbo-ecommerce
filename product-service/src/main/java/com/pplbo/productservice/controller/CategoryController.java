package com.pplbo.productservice.controller;

import com.pplbo.productservice.dto.CreateCategoryRequest;
import com.pplbo.productservice.model.Category;
import com.pplbo.productservice.service.CategoryService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;  // Add this import
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public Category getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CreateCategoryRequest request) {
        try {
            Category category = new Category();
            category.setCategoryName(request.categoryName());
            Category createdCategory = categoryService.saveCategory(category);
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to add category: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<?> patchCategory(@PathVariable Long categoryId, @RequestBody Map<String, Object> updates) {
        try {
            Category updatedCategory = categoryService.patchCategory(categoryId, updates);
            return ResponseEntity.ok(updatedCategory);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to update category: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{categoryId}")
    public Map<String, Boolean> deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
