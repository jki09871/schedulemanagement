package com.sparta.schedulemanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor  // 기본 생성자 추가
public class UserRequestDto {

    private String email;
    private String username;
    private String password;

    public UserRequestDto(UserRequestDto userRequestDto, String encryption){
        this.email = userRequestDto.getEmail();
        this.username = userRequestDto.getUsername();
        this.password = encryption;
    }

}
