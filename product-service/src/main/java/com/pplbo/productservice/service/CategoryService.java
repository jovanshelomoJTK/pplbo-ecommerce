package com.pplbo.productservice.service;

import java.util.List; 
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pplbo.productservice.model.Category;
import com.pplbo.productservice.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    public Category saveCategory(Category category) {
        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name is mandatory");
        }
        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name already exists");
        }
        return categoryRepository.save(category);
    }

    public Category patchCategory(Long categoryId, Map<String, Object> updates) {
        Category existingCategory = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        if (updates.containsKey("categoryName")) {
            String newCategoryName = (String) updates.get("categoryName");
            if (categoryRepository.existsByCategoryName(newCategoryName)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name already exists");
            }
            existingCategory.setCategoryName(newCategoryName);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid field: " + updates.keySet().toString());
        }

        return categoryRepository.save(existingCategory);
    }

    public Map<String, Boolean> deleteCategory(Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    
        categoryRepository.delete(existingCategory);
        return Map.of("deleted", Boolean.TRUE);
    }    
}