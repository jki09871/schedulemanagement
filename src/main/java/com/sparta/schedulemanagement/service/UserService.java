package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.config.PasswordEncoder;
import com.sparta.schedulemanagement.dto.LoginRequestDto;
import com.sparta.schedulemanagement.dto.UserRequestDto;
import com.sparta.schedulemanagement.dto.UserResponseDto;
import com.sparta.schedulemanagement.entity.User;
import com.sparta.schedulemanagement.entity.UserRoleEnum;
import com.sparta.schedulemanagement.jwt.JwtUtil;
import com.sparta.schedulemanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public UserResponseDto userCreated(UserRequestDto requestDto) {
        
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(requestDto.getUsername());
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        Optional<User> checkEmail = userRepository.findByEmail(requestDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        UserRequestDto userRequestDto = new UserRequestDto(requestDto, password, role);
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
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);
        res.setHeader("Authorization", "Bearer " + token);
        System.out.println("token = " + token);
    }
}
