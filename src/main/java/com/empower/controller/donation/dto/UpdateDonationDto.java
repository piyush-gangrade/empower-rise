package com.empower.controller.donation.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateDonationDto {
    private String title;
    private Long amount;
    private MultipartFile[] images;
    private String description;
    private Long categoryId;
    private Date dayLeft;
    private String location;
}
