package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.LoginRequestDto;
import com.sparta.schedulemanagement.dto.UserRequestDto;
import com.sparta.schedulemanagement.dto.UserResponseDto;
import com.sparta.schedulemanagement.jwt.JwtUtil;
import com.sparta.schedulemanagement.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("/users")
    public UserResponseDto userCreated(@RequestBody UserRequestDto userReqDto, HttpServletResponse res) {
        // Jwt 생성
        String token = jwtUtil.createToken(userReqDto.getUsername());

        // Jwt 쿠키 저장
        jwtUtil.addJwtToCookie(token, res);
        System.out.println("token = " + token);


        return userService.userCreated(userReqDto);
    }

    @GetMapping("/users/get-jwt")
    public String getJwt(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);

        // 토큰 검증
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Token Error");
        }

        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 username
        String username = info.getSubject();
        System.out.println("username = " + username);
        // 사용자 권한
//        String authority = (String) info.get(JwtUtil.AUTHORIZATION_KEY);
//        System.out.println("authority = " + authority);

        return "getJwt : " + username;
    }

    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        try {
            userService.login(requestDto, res);
            return ResponseEntity.ok("로그인 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 이메일 또는 비밀번호입니다.");
        }
    }

    @GetMapping("/users")
    public List<UserResponseDto> userAllLook() {
        return userService.userAllLook();
    }

    @GetMapping("/users/{id}")
    public UserResponseDto findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/users/{id}")
    public UserResponseDto userModify(@PathVariable Long id, @RequestBody UserRequestDto userReqDto) {
        return userService.userModify(id, userReqDto);
    }

    @DeleteMapping("/users/{id}")
    public String userDelete(@PathVariable Long id) {
        return userService.userDelete(id);
    }


}
