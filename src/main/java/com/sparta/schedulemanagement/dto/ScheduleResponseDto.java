package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.entity.ScheduleComment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String username;
    private String title;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private int commentsCount;

    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.username = schedule.getUsername();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.created_at = schedule.getCreatedAt();
        this.updated_at = schedule.getUpdatedAt();
        this.commentsCount = schedule.getScheduleComments().size();

    }

}
