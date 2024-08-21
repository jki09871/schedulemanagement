package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String username;
    private String title;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.username = schedule.getUsername();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.created_at = schedule.getCreated_at();
        this.updated_at = schedule.getUpdated_at();

    }

}
