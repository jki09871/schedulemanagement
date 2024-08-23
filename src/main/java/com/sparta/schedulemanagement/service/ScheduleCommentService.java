package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.dto.ScheduleCommentRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleCommentResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.entity.ScheduleComment;
import com.sparta.schedulemanagement.repository.ScheduleCommentRepository;
import com.sparta.schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleCommentService {

    private final ScheduleCommentRepository commentRepository;

    private final ScheduleRepository scheduleRepository;
    public ScheduleCommentResponseDto commentCreate(ScheduleCommentRequestDto commentReqDto) {

        Schedule schedule = scheduleRepository.findById(commentReqDto.getSchedule_id()).orElseThrow();

        ScheduleComment comment = new ScheduleComment(commentReqDto, schedule);

        commentRepository.save(comment);


        return new ScheduleCommentResponseDto(comment);

    }
}
