package com.sparta.schedulemanagement.dto;

import lombok.Getter;

@Getter
public class ScheduleCommentRequestDto {

    private String content;
    private String username;
    private Long scheduleId;
    private String password;



}
