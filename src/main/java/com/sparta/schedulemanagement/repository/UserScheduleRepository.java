package com.sparta.schedulemanagement.repository;

import com.sparta.schedulemanagement.dto.UserDetailsDto;
import com.sparta.schedulemanagement.entity.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {


    UserSchedule findByUser_Id(Long id);
}
