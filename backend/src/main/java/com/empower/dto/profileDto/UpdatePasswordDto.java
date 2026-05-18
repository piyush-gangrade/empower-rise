package com.empower.dto.profileDto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class UpdatePasswordDto {
    @NotNull(message = "Old password is required")
    private String oldPassword;

    @NotNull(message = "New Password is required")
    @Length(min = 6, max = 20, message = "Password must be greater than 6")
    private String newPassword;

}
