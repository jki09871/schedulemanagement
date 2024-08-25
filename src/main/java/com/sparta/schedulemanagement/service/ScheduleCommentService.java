package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.dto.ScheduleCommentRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleCommentResponseDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.entity.ScheduleComment;
import com.sparta.schedulemanagement.repository.ScheduleCommentRepository;
import com.sparta.schedulemanagement.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<ScheduleCommentResponseDto> commentFindBy() {

            return commentRepository.findAll()
                    .stream()
                    .map(ScheduleCommentResponseDto::new)
                    .toList();
    }

    public ScheduleCommentResponseDto commentFindById(Long id) {
        return commentRepository.findById(id)
                .map(ScheduleCommentResponseDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found")); // 값이 없을 때 예외 처리

    }

    @Transactional
    public ScheduleCommentResponseDto commentModify(Long id, ScheduleCommentRequestDto commentReqDto) {

        ScheduleComment scheduleComment = commentRepository.findById(id).orElseThrow();

        scheduleComment.commentsModify(commentReqDto.getContent());

        return new ScheduleCommentResponseDto(scheduleComment);
    }
}
