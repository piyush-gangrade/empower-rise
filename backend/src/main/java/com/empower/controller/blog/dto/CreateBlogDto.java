package com.empower.controller.blog.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBlogDto {

    @NotNull(message = "Blog name is required")
    private String name;

    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Images are required")
    private MultipartFile[] images;

    @NotNull(message = "Category is required")
    private Long categoryId; // Use camelCase for consistency

    @NotNull(message = "Description is required")
    private String description;
}

