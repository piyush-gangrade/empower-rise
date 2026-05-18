package com.empower.controller.comment.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateCommentDto {
    private Long donationId;
    private Long fundId;
    private MultipartFile[] images;
    private String text;
    private Long userId;

}
