package com.empower.controller.donation.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.empower.controller.category.Category;
import com.empower.controller.user.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDonationDto {
    @NotNull(message = "Donation title is required")
    private String title;

    @NotNull(message = "Amount is required")
    private Long amount;

    @NotNull(message = "Images are required")
    private MultipartFile[] images;

    @NotNull(message = "Donation description is required")
    private String description;

    @NotNull(message = "user is required")
    private Long userId;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "Day left field is required")
    private Date dayLeft;

    @NotNull(message = "Location field is required")
    private String location;
}