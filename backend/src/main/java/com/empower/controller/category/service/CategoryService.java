package com.empower.controller.category.service;

import org.springframework.stereotype.Service;

import com.empower.controller.category.Category;
import com.empower.controller.category.dto.CategoryDto;
import com.empower.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto data) {
        if (categoryRepository.existsByName(data.getName())) {
            throw new ResourceNotFoundException("Category already exists");
        }
        Category category = new Category();
        category.setName(data.getName());
        category.setPhotoURL(data.getPhotoURL());
        categoryRepository.save(category);
        return category;
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }


    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }


    public Category updateCategory(Long categoryId, CategoryDto data) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        
        if (data.getName() != null) {
            category.setName(data.getName());
        }

        if (data.getPhotoURL()!= null) {
            category.setPhotoURL(data.getPhotoURL());
        }

        return categoryRepository.save(category);
    }
    

    public void deleteCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        if(category == null) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.delete(category);
    }

}
