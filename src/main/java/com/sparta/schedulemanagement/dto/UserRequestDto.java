package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor  // 기본 생성자 추가
public class UserRequestDto {

    private String email;
    private String username;
    private String password;

    private boolean admin = false;

    private String adminToken = "";
    private UserRoleEnum role;

    public UserRequestDto(UserRequestDto userRequestDto, String encryption, UserRoleEnum role){
        this.email = userRequestDto.getEmail();
        this.username = userRequestDto.getUsername();
        this.password = encryption;
        this.role = role;
    }


}
