package org.jianeng.books.mapper;

import lombok.extern.slf4j.Slf4j;
import org.jianeng.books.model.Role;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/7 12:00
 */
@SpringBootTest
@Slf4j
public class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    private Logger logger = LoggerFactory.getLogger(RoleMapperTest.class);

    private static final String ROLE_NAME = "ROLE_TEST_123";
    private static final String NAME = "测试用户";
    private static final String THEN_NAME = "测试用户1";



    @Transactional
    @Rollback
    @Test
    public void testRoleMapper() {
        // insert
        Role role = new Role();
        role.setId(null);
        role.setName(NAME);
        role.setRoleName(ROLE_NAME);
        int res = roleMapper.insert(role);
        Assert.assertTrue(res > 0);
        int id = role.getId();
        logger.info("id: " + id);
        logger.info(role.toString());
        Assert.assertTrue(id > 0);

        // query
        Role now = roleMapper.selectById(id);
        Assert.assertEquals(role, now);

        now.setName(THEN_NAME);
        // update
        roleMapper.updateById(now);
        Role now_role = roleMapper.selectById(id);
        Assert.assertEquals(now, now_role);


        // delete
        roleMapper.deleteById(id);
        now = roleMapper.selectById(id);
        Assert.assertNull(now);
    }
}
