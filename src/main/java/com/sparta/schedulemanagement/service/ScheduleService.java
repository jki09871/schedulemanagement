package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.schedulemanagement.dto.UserDetailsDto;
import com.sparta.schedulemanagement.entity.Schedule;
import com.sparta.schedulemanagement.entity.User;
import com.sparta.schedulemanagement.entity.UserRoleEnum;
import com.sparta.schedulemanagement.entity.UserSchedule;
import com.sparta.schedulemanagement.jwt.JwtUtil;
import com.sparta.schedulemanagement.repository.ScheduleRepository;
import com.sparta.schedulemanagement.repository.UserRepository;
import com.sparta.schedulemanagement.repository.UserScheduleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final UserScheduleRepository userScheduleRepository;

    private final JwtUtil jwtUtil;


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
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        List<UserDetailsDto> assignedUsers = new ArrayList<>();

        for (UserSchedule userSchedule : schedule.getUserSchedules()) {
            User user = userSchedule.getUser();
            UserDetailsDto userDetailsDto = new UserDetailsDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail()
            );
            assignedUsers.add(userDetailsDto);
        }
        return new ScheduleResponseDto(schedule, assignedUsers);
    }

    @Transactional
    public ScheduleResponseDto modifySchedule(Long id, ScheduleRequestDto scheduleRequestDto, HttpServletRequest req) {

        UserRoleEnum role = adminCheck(req);

        if (UserRoleEnum.ADMIN.equals(role)) {
            Schedule schedule = scheduleRepository.findById(id).orElseThrow();

            schedule.modifySchedule(scheduleRequestDto.getTitle(), scheduleRequestDto.getContent());
            return new ScheduleResponseDto(schedule);
        }
         throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have administrator privileges.");
    }


    public String scheduleDelete(Long id, HttpServletRequest req) {
        UserRoleEnum role = adminCheck(req);

        if (UserRoleEnum.ADMIN.equals(role)) {

            if (readSchedule(id) != null) {
                scheduleRepository.deleteById(id);
                return id + "번 스케줄 삭제 완료!";
            }
            return null;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have administrator privileges.");
    }

    public UserRoleEnum adminCheck(HttpServletRequest req){
        String token = jwtUtil.getTokenFromRequest(req);
        String roleStr = jwtUtil.extractUserRole(token);

        UserRoleEnum role;
        try {
            role = UserRoleEnum.valueOf(roleStr);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid role in token.");
        }

        return role;
    }
}
