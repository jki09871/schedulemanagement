package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.config.PasswordEncoder;
import com.sparta.schedulemanagement.dto.LoginRequestDto;
import com.sparta.schedulemanagement.dto.UserRequestDto;
import com.sparta.schedulemanagement.dto.UserResponseDto;
import com.sparta.schedulemanagement.entity.User;
import com.sparta.schedulemanagement.jwt.JwtUtil;
import com.sparta.schedulemanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public UserResponseDto userCreated(UserRequestDto userReqDto) {
        String encryption = passwordEncoder.encode(userReqDto.getPassword());
        UserRequestDto userRequestDto = new UserRequestDto(userReqDto, encryption);
        User user = new User(userRequestDto);
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public List<UserResponseDto> userAllLook() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .toList();
    }

    public UserResponseDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(UserResponseDto::new)
                .orElseThrow(() -> new EntityNotFoundException("User not found")); // 값이 없을 때 예외 처리
    }

    @Transactional
    public UserResponseDto userModify(Long id, UserRequestDto userReqDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.modifyUser(userReqDto);
        return new UserResponseDto(user);
    }

    public String userDelete(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        if (user != null) {
            userRepository.deleteById(id);
            return user.getUsername() + " 이름을 가진 사용자가 삭제 되었습니다.";
        }
        return null;
    }

    public void login(LoginRequestDto requestDto, HttpServletResponse res) {

        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        //사용자 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        //비밀번 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //JWT 생성 및 쿠키에 저장
        String token = jwtUtil.createToken(user.getUsername());
        System.out.println("token = " + token);
        jwtUtil.addJwtToCookie(token, res);
        res.setHeader("Authorization", "Bearer " + token);
    }
}
