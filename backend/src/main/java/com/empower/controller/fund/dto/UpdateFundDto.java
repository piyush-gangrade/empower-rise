package com.empower.controller.fund.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // Added this to prevent Spring instantiation errors
public class UpdateFundDto {
    private String title;
    private Long amount;
    private MultipartFile[] images;
    private String description;
    private Long categoryId;

    // Explicitly tell Spring how to read the form data string
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dayLeft;

    private String location;
}