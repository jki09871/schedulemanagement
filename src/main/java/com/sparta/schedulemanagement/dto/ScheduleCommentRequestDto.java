package com.sparta.schedulemanagement.dto;

import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.entity.ScheduleComment;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleCommentRequestDto {

    private Long id;
    private String content;
    private String user_name;
    private Long schedule_id;



}
