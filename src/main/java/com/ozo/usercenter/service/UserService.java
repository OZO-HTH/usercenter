package com.ozo.usercenter.service;

import com.ozo.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 64433
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2022-10-27 12:56:06
*/
public interface UserService extends IService<User> {



    /**
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 确认密码
     * @return 用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * @param userAccount        用户账号
     * @param userPassword       用户密码
     * @param httpServletRequest
     * @return 用户信息(脱敏)
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest);

    public User getSafetyUser(User originalUser);
}
