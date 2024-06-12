package com.pplbo.productservice.service;

import com.pplbo.productservice.model.Category;
import com.pplbo.productservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category patchCategory(Long categoryId, Map<String, Object> updates) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        updates.forEach((key, value) -> {
            if (key.equals("categoryName")) {
                category.setCategoryName((String) value);
            }
        });

        return categoryRepository.save(category);
    }

    public Map<String, Boolean> deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        categoryRepository.delete(category);

        return Map.of("deleted", Boolean.TRUE);
    }
}
