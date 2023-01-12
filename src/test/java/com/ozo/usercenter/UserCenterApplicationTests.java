package com.ozo.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void testMD5() {
        String newPassword= DigestUtils.md5DigestAsHex("abcd".getBytes());
        System.out.println(newPassword);

    }

    @Test
    void contextLoads() {
    }

}
