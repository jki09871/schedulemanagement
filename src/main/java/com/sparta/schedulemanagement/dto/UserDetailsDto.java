package com.sparta.schedulemanagement.dto;

import lombok.Getter;

@Getter
public class UserDetailsDto {
    private Long id;
    private String username;
    private String email;

    public UserDetailsDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

}
