package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.entity.ScheduleComment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleCommentResponseDto {

    private Long id;
    private String content;
    private String user_name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    private Long schedule_id;

    public ScheduleCommentResponseDto(ScheduleComment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.user_name = comment.getUser_name();
        this.created_at = comment.getCreated_at();
        this.updated_at = comment.getUpdated_at();
//        this.schedule_id = comment.getSchedule().getId();
    }

}
