package com.sparta.schedulemanagement.controller;

import com.sparta.schedulemanagement.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    @GetMapping("/products")
    public User getProducts(HttpServletRequest req) {
        System.out.println("ProductController.getProducts : 인증 완료");
        User user = (User) req.getAttribute("user");
        return user;
    }
}