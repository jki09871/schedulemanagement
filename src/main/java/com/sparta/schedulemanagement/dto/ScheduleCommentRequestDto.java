package com.sparta.schedulemanagement.dto;

import lombok.Getter;

@Getter
public class ScheduleCommentRequestDto {

    private Long id;
    private String content;
    private String user_name;
    private Long schedule_id;



}
