package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.ScheduleCommentRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleCommentResponseDto;
import com.sparta.schedulemanagement.service.ScheduleCommentService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleCommentController {

    private final ScheduleCommentService commentService;

    @PostMapping("/comments")
    public ScheduleCommentResponseDto commentCreate(@RequestBody ScheduleCommentRequestDto commentReqDto){
        return commentService.commentCreate(commentReqDto);
    }

    @GetMapping("/comments")
    public List<ScheduleCommentResponseDto> commentFindBy(){
        return commentService.commentFindBy();
    }

    @GetMapping("/comments/{id}")
    public ScheduleCommentResponseDto commentFindById(@PathVariable Long id){
        return commentService.commentFindById(id);
    }

    @PutMapping("/comments/{id}")
    public ScheduleCommentResponseDto commentModify(@PathVariable Long id,
                                                    @RequestBody ScheduleCommentRequestDto commentReqDto){
        return commentService.commentModify(id, commentReqDto);
    }

}
