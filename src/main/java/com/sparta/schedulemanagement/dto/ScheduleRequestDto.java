package com.sparta.schedulemanagement.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleRequestDto {

    private String title;
    private String content;
    private List<Long> assignedUserIds;

    public ScheduleRequestDto(String title, String content, List<Long> assignedUserIds) {
        this.title = title;
        this.content = content;
        this.assignedUserIds = assignedUserIds;
    }
}
