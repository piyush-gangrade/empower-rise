package com.empower.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.empower.dto.profileDto.UpdateUserDto;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateUsersDto extends UpdateUserDto {
    private String photoURL;
    private String address;
}