package com.empower.controller.user.dto;

import com.empower.dto.profileDto.CreateUserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CreateUsersDto extends CreateUserDto {
    private String photoURL;
    private String address;
}
