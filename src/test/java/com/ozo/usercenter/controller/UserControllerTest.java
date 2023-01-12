package com.ozo.usercenter.controller;

import com.ozo.usercenter.model.domain.User;
import com.ozo.usercenter.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Resource
    private UserService userService;
    @Test
    void getUserInfo() {
        User user = userService.getById(2);
        System.out.println(userService.getSafetyUser(user));
    }
}