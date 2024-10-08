package com.sparta.schedulemanagement.repository;

import com.sparta.schedulemanagement.entity.ScheduleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleCommentRepository extends JpaRepository<ScheduleComment, Long> {

}
