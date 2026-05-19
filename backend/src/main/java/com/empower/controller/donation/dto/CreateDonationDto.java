package com.empower.controller.donation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDonationDto {

    @NotNull(message = "Fund ID is required")
    private Long fundId;

    @NotNull(message = "Donation amount is required")
    @Min(value = 1, message = "Donation must be greater than 0")
    private Double amount;

    private String donorName;

    private Boolean anonymous;

    private String message;

    // If the user is logged in, you can pass their ID to link it to their account
    private Long userId;
}