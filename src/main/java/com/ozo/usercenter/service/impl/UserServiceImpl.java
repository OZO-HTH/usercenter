package com.ozo.usercenter.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ozo.usercenter.model.domain.User;
import com.ozo.usercenter.service.UserService;
import com.ozo.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

import static com.ozo.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author 64433
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2022-10-27 12:56:06
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    private static final String SALT = "ozo";


    /**
     *
     * 用户注册
     * @Author Ozo
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 确认密码
     * @return 新用户id
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //校验用户名和密码是否为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        //校验用户名长度
        if (userAccount.length() < 4) {
            return -1;
        }
        //校验密码长度
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }
        //校验两次密码是否一致
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if(count > 0) {
            return -1;
        }

        //密码加密(千万不要明文存储密码)
        String newPassword= DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());


        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        boolean save = this.save(user);
        if (!save) {
            return -1;
        }
        return user.getId();
    }


    /**
     * 用户登录
     *
     * @param userAccount        用户账号
     * @param userPassword       用户密码
     * @param httpServletRequest 服务器请求
     * @return 用户信息(脱敏)
     */
    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        //校验用户名长度和密码长度
        if (userAccount.length() < 4 || userPassword.length() < 8) {
            return null;
        }


        //验证密码是否正确
        String newPassword= DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", newPassword);
        User user = this.getOne(queryWrapper);
        if (user == null) {
            log.info("User login failed! userAccount cannot match userPassword!");
            return null;
        }


        //用户脱敏, 防止用户态中的数据泄露给前端
        User safetyUser = getSafetyUser(user);

        //记录用户的登录态
        httpServletRequest.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        return safetyUser;
    }

    public User getSafetyUser(User originalUser) {
        User safetyUser =  new User();
        safetyUser.setId(originalUser.getId());
        safetyUser.setUserRole(originalUser.getUserRole());
        safetyUser.setUsername(originalUser.getUsername());
        safetyUser.setUserAccount(originalUser.getUserAccount());
        safetyUser.setGender(originalUser.getGender());
        safetyUser.setAvatarUrl(originalUser.getAvatarUrl());
        safetyUser.setEmail(originalUser.getEmail());
        safetyUser.setPhone(originalUser.getPhone());
        safetyUser.setCreateTime(originalUser.getCreateTime());
        safetyUser.setUserStatus(originalUser.getUserStatus());
        return safetyUser;
    }
}




