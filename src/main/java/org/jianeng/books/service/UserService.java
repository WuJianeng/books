package org.jianeng.books.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jianeng.books.mapper.UserMapper;
import org.jianeng.books.model.User;
import org.jianeng.books.model.exception.RequestValidationFailedException;
import org.jianeng.books.model.exception.UserAlreadyExsitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 16:12
 */

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    public static final String USERNAME = "username:";
    private UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * @param user
     * @return
     */
    public Boolean login(User user) {
        logger.info("username: " + user.getName() + " password: " + user.getPassword());
        return true;
    }

    /**
     * 根据用户 id 查询用户
     * @param userId
     * @return
     */
    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    public User getUserByUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new RequestValidationFailedException(ImmutableMap.of("username", userName));
        }
        userName = userName.trim();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name", userName);
        return userMapper.selectOne(wrapper);
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()
                || user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new RequestValidationFailedException(ImmutableMap.of("user:", user));
        }
        ensureUserNameNotExist(user.getName());

        user.setId(null);
        userMapper.insert(user);
        logger.info("创建用户 name: " + user.getName() + " email: " + user.getEmail());
        return true;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public boolean updateUser(User user) {
        if (user.getId() == null || user.getName() == null || user.getName().trim().isEmpty()) {
            throw new RequestValidationFailedException(ImmutableMap.of("user is illegal.", user));
        }

        User old = userMapper.selectById(user.getId());
        if (old == null || !old.getName().equals(user.getName())) {
            throw new RequestValidationFailedException(ImmutableMap.of("user is illegal.", user));
        }

        int res = userMapper.updateById(user);
        if (res <= 0) {
            throw new RequestValidationFailedException(ImmutableMap.of("update fail.", user));
        }
        return true;
    }

    private void ensureUserNameNotExist(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name", username);
        User user =  userMapper.selectOne(wrapper);
        if (user != null) {
            throw new UserAlreadyExsitException(ImmutableMap.of("username", username));
        }
    }
}
