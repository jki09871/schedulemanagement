package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private Long id;
    private String email;
    private String username;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.created_at = user.getCreatedAt();
        this.updated_at = user.getUpdatedAt();
    }
}
