package com.empower.controller.blog.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdateBlogDto {
    private String name;
    private String title;
    private String description;
    private MultipartFile[] images;
    private Long categoryId;
}
