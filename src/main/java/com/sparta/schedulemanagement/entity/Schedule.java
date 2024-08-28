package com.sparta.schedulemanagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.schedulemanagement.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * CREATE TABLE Schedule
 * (
 * id         BIGINT AUTO_INCREMENT PRIMARY KEY,
 * user_id    BIGINT       NOT NULL,
 * title      VARCHAR(255) NOT NULL,
 * content    TEXT,
 * created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 * updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 * FOREIGN KEY (user_id) REFERENCES User (id)
 * );
 */
@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Table(name = "Schedule") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<UserSchedule> userSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<ScheduleComment> scheduleComments = new ArrayList<>();





    public Schedule(ScheduleRequestDto scheduleRequestDto, User user) {
        this.title = scheduleRequestDto.getTitle();
        this.content = scheduleRequestDto.getContent();
        this.user = user;

    }


    public void modifySchedule(String title, String content) {
        if (title != null) {
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
    }
}
