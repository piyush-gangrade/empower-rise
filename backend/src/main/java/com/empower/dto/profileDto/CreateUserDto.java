package com.empower.dto.profileDto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserDto {
    @NotNull(message = "User name is required")
    @Length(max = 30, min = 3, message = "Name greater than 3 and lower than 30")
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Require a valid email")
    private String email;
    
    @NotNull(message = "Phone number is required")
    @Length(min = 11, max = 14, message = "Phone number is not lower that 11 and not grater that 14")
    private String phoneNumber;

    private String password;
}
