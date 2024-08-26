package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;


    @PostMapping("/schedules")
    public ScheduleResponseDto scheduleCreated(@RequestBody ScheduleRequestDto scheduleRequestDto, Long userId) {
        return scheduleService.scheduleWrite(scheduleRequestDto, userId);
    }

    @GetMapping("/schedules")
    public Page<ScheduleResponseDto> getSchedulePaging(
            @RequestParam("page") int page,
            @RequestParam(name = "size", value = "size", defaultValue = "10") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc) {
        return scheduleService.getSchedulePaging(page - 1, size, sortBy, isAsc);
    }

    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto readSchedule(@PathVariable Long id) {
        return scheduleService.readSchedule(id);
    }

    @PutMapping("/schedules/{id}")
    public ScheduleResponseDto scheduleModify(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.modifySchedule(id, scheduleRequestDto);
    }

    @DeleteMapping("/schedules/{id}")
    public String scheduleDelete(@PathVariable("id") Long id) {
        return scheduleService.scheduleDelete(id);
    }
}
