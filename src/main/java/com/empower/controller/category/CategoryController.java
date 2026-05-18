package com.empower.controller.category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.empower.controller.category.dto.CategoryDto;
import com.empower.controller.category.service.CategoryService;
import com.empower.exceptions.AlreadyExistsException;
import com.empower.exceptions.ResourceNotFoundException;
import com.empower.response.ApiResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryDto data) {
        try {
            Category newCategory = categoryService.createCategory(data);
            return ResponseEntity.ok(new ApiResponse("Category Created", false, newCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(400).body(new ApiResponse("Category already exists", false, null));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Category found", true, category));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse("Category not found", false, null));
        }
    }
    
    @GetMapping("/byName")
    public ResponseEntity<ApiResponse> getCategoryByName(@RequestBody CategoryDto data) {
        try {
            Category category = categoryService.getCategoryByName(data.getName());
            return ResponseEntity.ok(new ApiResponse("Category Found", true, category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage(), false, data));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategory() {
        try {
            List<Category> categoryList = categoryService.getAllCategory();
            return ResponseEntity.ok(new ApiResponse("All Category List", true, categoryList));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage(), false, categoryService));
        }
    }
    
    @PatchMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto data) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                return ResponseEntity.status(404).body(new ApiResponse("Category not found", false, null));
            }
            Category updateCategory = categoryService.updateCategory(categoryId, data);
            return ResponseEntity.status(200).body(new ApiResponse("Category updated", true, updateCategory));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                return ResponseEntity.status(404).body(new ApiResponse("Category not found ", true, null));
            }
            return ResponseEntity.status(200).body(new ApiResponse("Category Found", true, null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage(), false, null));
        }
    }

}
