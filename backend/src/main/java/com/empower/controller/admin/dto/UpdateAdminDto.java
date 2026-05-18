package com.empower.controller.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateAdminDto {
    private String name;
    private String email;
    private String phoneNumber;
}