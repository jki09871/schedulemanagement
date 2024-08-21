package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;



    @PostMapping("/schedules")
    public ScheduleResponseDto scheduleCreate(@RequestBody ScheduleRequestDto scheduleRequestDto) {
       return scheduleService.scheduleWrite(scheduleRequestDto);
    }

    @GetMapping("/schedules/{id}")
    public Schedule readSchedule(@PathVariable Long id){
        return scheduleService.readSchedule(id);
    }

    @PutMapping("/schedules/{id}")
    public Schedule scheduleModify(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto){
        return scheduleService.scheduleUpdate(id,  scheduleRequestDto);
    }
}
