package com.ozo.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ozo.usercenter.model.domain.User;
import com.ozo.usercenter.model.domain.request.UserLoginRequest;
import com.ozo.usercenter.model.domain.request.UserRegisterRequest;
import com.ozo.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ozo.usercenter.constant.UserConstant.ADMIN;
import static com.ozo.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long id = userService.userRegister(userAccount, userPassword, checkPassword);
        return id;
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.doLogin(userAccount, userPassword, request);
    }

    @GetMapping("/search")
    public List<User> searchUses(String username, HttpServletRequest httpServletRequest) {
        //仅管理员可登录
        if (!isAdmin(httpServletRequest)) {
            return new ArrayList<>();
        }


        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);

        return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest httpServletRequest) {

        if (!isAdmin(httpServletRequest)) {
            return false;
        }
        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }

    @GetMapping("/getCurrentUser")
    public User getCurrentUser(HttpServletRequest httpServletRequest) {
        Long userId = isLogin(httpServletRequest);
        if (userId > 0) {
            return userService.getSafetyUser(userService.getById(userId));

        }
        return null;
    }


    private boolean isAdmin(HttpServletRequest httpServletRequest) {
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN;
    }

    private long isLogin(HttpServletRequest httpServletRequest) {
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user != null) {
            return user.getId();
        }
        return -1;
    }

}
