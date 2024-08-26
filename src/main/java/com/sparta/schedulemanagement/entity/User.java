package com.sparta.schedulemanagement.entity;

import com.sparta.schedulemanagement.dto.UserRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Table(name = "user") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
@AllArgsConstructor
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<UserSchedule> userSchedules;

    public User(UserRequestDto userRequestDto){
        this.username = userRequestDto.getUsername();
        this.email = userRequestDto.getEmail();
    }

    public void modifyUser(UserRequestDto userRequestDto){
        if (userRequestDto.getEmail() != null){
            this.email = userRequestDto.getEmail();
        }

        if (userRequestDto.getUsername() != null){
            this.username = userRequestDto.getUsername();
        }

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", userSchedules=" + userSchedules +
                '}';
    }


}
