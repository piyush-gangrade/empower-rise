package com.empower.controller.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryDto {
    @NotNull(message = "Category name is required")
    private String name;

    private String photoURL;
}
