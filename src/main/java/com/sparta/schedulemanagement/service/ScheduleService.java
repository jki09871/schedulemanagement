package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.dto.UserDetailsDto;
import com.sparta.schedulemanagement.dto.UserResponseDto;
import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.entity.User;
import com.sparta.schedulemanagement.entity.UserSchedule;
import com.sparta.schedulemanagement.repository.ScheduleRepository;
import com.sparta.schedulemanagement.repository.UserRepository;
import com.sparta.schedulemanagement.repository.UserScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final UserScheduleRepository userScheduleRepository;


    public ScheduleResponseDto scheduleWrite(ScheduleRequestDto scheduleRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Schedule schedule = new Schedule(scheduleRequestDto, user);
        scheduleRepository.save(schedule);

        // 추가로 일정에 유저들을 배치하는 로직 (예시)
        for (Long assignedUserId : scheduleRequestDto.getAssignedUserIds()) {
            User assignedUser = userRepository.findById(assignedUserId).orElseThrow(() -> new IllegalArgumentException("Assigned user not found"));
            UserSchedule userSchedule = new UserSchedule(schedule, assignedUser);
            userScheduleRepository.save(userSchedule);
        }

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public Page<ScheduleResponseDto> getSchedulePaging(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Schedule> scheduleList = scheduleRepository.findAll(pageable);
        return scheduleList.map(ScheduleResponseDto::new);
    }

    public ScheduleResponseDto readSchedule(Long id) {
        // Schedule 엔티티를 조회
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        // 유저 정보를 담을 리스트 생성
        List<UserDetailsDto> assignedUsers = new ArrayList<>();

        // 각 UserSchedule에서 User 정보를 추출하여 UserDetailsDto로 변환하고 리스트에 추가
        for (UserSchedule userSchedule : schedule.getUserSchedules()) {
            User user = userSchedule.getUser();
            UserDetailsDto userDetailsDto = new UserDetailsDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail()
            );
            assignedUsers.add(userDetailsDto);
        }

        // ScheduleResponseDto를 생성하고, 유저 정보를 포함시켜 반환
        return new ScheduleResponseDto(schedule, assignedUsers);
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
