package org.jianeng.books.mapper;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.jianeng.books.model.UserRole;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/11 17:58
 */
@SpringBootTest
@Slf4j
class UserRoleMapperTest {

    @Autowired
    private UserRoleMapper userRoleMapper;

    private Logger logger = LoggerFactory.getLogger(UserRoleMapperTest.class);

    @Test
    public void getUserRoleById_1() {
        UserRole userRole = userRoleMapper.selectById(1);
        logger.info(userRole.toString());
        Assert.assertNotNull(userRole);
        Assert.assertNotNull(userRole.getId());
        Assert.assertNotNull(userRole.getUserId());
        Assert.assertNotNull(userRole.getRoleId());

        // user selectBatchIds
        List<UserRole> userRoles = userRoleMapper.selectBatchIds(ImmutableList.of(1));
        Assert.assertNotNull(userRoles);
        userRole = userRoles.get(0);
        logger.info(userRole.toString());
        Assert.assertNotNull(userRole);
        Assert.assertNotNull(userRole.getId());
        Assert.assertNotNull(userRole.getUserId());
        Assert.assertNotNull(userRole.getRoleId());
    }

}