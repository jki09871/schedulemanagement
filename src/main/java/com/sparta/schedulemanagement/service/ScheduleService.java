package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto scheduleWrite(ScheduleRequestDto scheduleRequestDto) {

        // RequestDto -> Entity
        Schedule schedule = new Schedule(scheduleRequestDto);

        scheduleRepository.save(schedule);

        // Entity -> RequestDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    public Schedule readSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                 new EntityNotFoundException("해당하는 아디를 가진 스케줄이 없습니다."));

    }

    @Transactional
    public Schedule scheduleUpdate(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = readSchedule(id);

        if (scheduleRequestDto.getTitle() != null) {
            schedule.setTitle(scheduleRequestDto.getTitle());
        }
        if (scheduleRequestDto.getContent() != null) {
            schedule.setContent(scheduleRequestDto.getContent());
        }

        return schedule;

    }
}
