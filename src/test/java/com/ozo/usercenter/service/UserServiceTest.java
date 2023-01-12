package com.ozo.usercenter.service;
import java.util.Date;

import com.ozo.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

//用户服务测试
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("testOzo");
        user.setUserPassword("123312");
        user.setUserAccount("13333333333");
        user.setGender(0);
        user.setAvatarUrl("http://www.baidu.com");
        user.setEmail("304354249@qq.com");
        user.setPhone("13333333333");
        user.setUserStatus(0);;
        user.setIsDelete(0);

        boolean save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertEquals(save, true);
    }

    @Test
    void userRegister() {
        long userId = userService.userRegister("testozo", "12345678", "12345678");
        Assertions.assertEquals(-1, userId);
        System.out.println(userId);

    }
}