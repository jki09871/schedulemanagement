package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int commentsCount;

    private List<UserDetailsDto> assignedUsers;


    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
        this.commentsCount = schedule.getScheduleComments().size();

    }
    public ScheduleResponseDto(Schedule schedule, List<UserDetailsDto> assignedUsers) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
        this.commentsCount = schedule.getScheduleComments().size();
        this.assignedUsers = assignedUsers;
    }

}
