package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.ScheduleComment;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleRequestDto {

    private String username;
    private String title;
    private String content;
}
