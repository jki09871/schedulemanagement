package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Page<ScheduleResponseDto> getSchedulePaging(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Schedule> scheduleList = scheduleRepository.findAll(pageable);
        return scheduleList.map(ScheduleResponseDto::new);
    }

    @Transactional
    public ScheduleResponseDto readSchedule(Long id) {

        Schedule schedule = scheduleRepository.findById(id).orElseThrow();

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;

    }

    @Transactional
    public ScheduleResponseDto modifySchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow();

        schedule.modifySchedule(scheduleRequestDto.getTitle(), scheduleRequestDto.getContent());

        return new ScheduleResponseDto(schedule);

    }

    public String scheduleDelete(Long id) {
        if (readSchedule(id) != null){
            scheduleRepository.deleteById(id);
            return id + "번 스케줄 삭제 완료!";
        }
        return null;
    }
}
