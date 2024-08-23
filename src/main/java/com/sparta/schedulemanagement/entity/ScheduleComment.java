package com.sparta.schedulemanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.schedulemanagement.dto.ScheduleCommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Table(name = "schedule_comment") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class ScheduleComment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "user_name", nullable = false)
    private String user_name;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @JsonBackReference

    private Schedule schedule;


    public ScheduleComment(ScheduleCommentRequestDto comment, Schedule schedule){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.user_name = comment.getUser_name();
        this.schedule = schedule;
    }

}
