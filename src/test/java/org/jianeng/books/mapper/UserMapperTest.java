package org.jianeng.books.mapper;

import lombok.extern.slf4j.Slf4j;
import org.jianeng.books.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/11 16:16
 */
@SpringBootTest
@Slf4j
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(UserMapperTest.class);

    @Test
    public void getUser() {
        User user = userMapper.selectById(1);
        logger.info(user.toString());

        Assert.assertNotNull(user);
        Assert.assertTrue(user.getId() == 1);
    }
}