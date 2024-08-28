package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.ScheduleComment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleCommentResponseDto {

    private Long id;
    private String content;
    private String user_name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long scheduleId;


    public ScheduleCommentResponseDto(ScheduleComment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.user_name = comment.getUser_name();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.scheduleId = comment.getSchedule().getId();
    }


}
