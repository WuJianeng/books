package org.jianeng.books.service;

import lombok.extern.slf4j.Slf4j;
import org.jianeng.books.model.Role;
import org.jianeng.books.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/7 16:37
 */
@SpringBootTest
@Slf4j
public class UserDetailsServiceTest {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserDetailsServiceTest.class);

    private static final String NAME = "user_test_12213";
    private static final String PASSWORD = "password_test";
    private static final String EMAIL = "test@test.com";

    private User mockUser() {
        User user = new User();
        user.setName(NAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setAge(30);
        user.setGender("M");
        return user;
    }

    @Transactional
    @Rollback
    @Test
    public void testLoadUserByUsername() {
        User user = mockUser();
        logger.info(user.toString());

        // 添加用户
        boolean res = userService.addUser(user);
        Assert.assertTrue(res);

        // 测试 loadUserByUsername
        User actual = (User) userDetailsService.loadUserByUsername(user.getUsername());
        Assert.assertEquals(user.getUsername(), actual.getUsername());
        logger.info(actual.toString());

        // load user with id 1
        actual = (User) userDetailsService.loadUserByUsername("Jone");
        logger.info(actual.toString());

        List<Role> roles = actual.getRoles();
        Assert.assertNotNull(roles);
        Assert.assertTrue(!roles.isEmpty());
        Assert.assertTrue(roles.get(0).getId() != 0);
        Assert.assertTrue(!roles.get(0).getRoleName().isEmpty());
        Assert.assertTrue(!roles.get(0).getName().isEmpty());
    }
}
