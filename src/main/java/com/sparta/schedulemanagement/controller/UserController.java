package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.dto.UserRequestDto;
import com.sparta.schedulemanagement.dto.UserResponseDto;
import com.sparta.schedulemanagement.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public UserResponseDto userCreated(@RequestBody UserRequestDto userReqDto) {
        return userService.userCreated(userReqDto);
    }

    @GetMapping("/users")
    public List<UserResponseDto> userAllLook(){
        return userService.userAllLook();
    }
    @GetMapping("/users/{id}")
    public UserResponseDto findUserById(@PathVariable Long id){
        return userService.findUserById(id);
    }

    @PutMapping("/users/{id}")
    public UserResponseDto userModify(@PathVariable Long id, @RequestBody UserRequestDto userReqDto) {
        return userService.userModify(id,userReqDto);
    }

    @DeleteMapping("/users/{id}")
    public String userDelete(@PathVariable Long id){
        return userService.userDelete(id);
    }
}
