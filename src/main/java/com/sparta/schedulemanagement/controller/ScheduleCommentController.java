package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.ScheduleCommentRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleCommentResponseDto;
import com.sparta.schedulemanagement.service.ScheduleCommentService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleCommentController {

    private final ScheduleCommentService commentService;

    @PostMapping("/comment")
    public ScheduleCommentResponseDto commentCreate(@RequestBody ScheduleCommentRequestDto commentReqDto){
        return commentService.commentCreate(commentReqDto);
    }
}
