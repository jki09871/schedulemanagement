package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

public class LoginRequestDto {
    private String email;
    private String password;
}