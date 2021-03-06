package org.jianeng.books.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.jianeng.books.dto.UserInfo;
import org.jianeng.books.mapper.RoleMapper;
import org.jianeng.books.mapper.UserMapper;
import org.jianeng.books.mapper.UserRoleMapper;
import org.jianeng.books.model.Role;
import org.jianeng.books.model.User;
import org.jianeng.books.model.UserRole;
import org.jianeng.books.model.exception.RequestValidationFailedException;
import org.jianeng.books.model.exception.UserAlreadyExsitException;
import org.jianeng.books.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 16:12
 */

@Service
@Slf4j
public class UserService {

    public static final String USERNAME = "username:";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password) {
        logger.info("???????????? username: " + username + " password: " + password);
        // ????????????????????????????????????
        UserDetails user = getUserByUserName(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("???????????????");
        }

        // ???????????????????????? token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token = tokenUtils.generateToken(user);

        return token;
    }

    /**
     * ???????????? id ????????????
     * @param userId
     * @return
     */
    public User getUserById(Integer userId) {
        return userMapper.selectById(userId);
    }

    /**
     * ???????????????????????????
     * @param userName
     * @return
     */
    public User getUserByUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new RequestValidationFailedException(ImmutableMap.of("username", userName));
        }
        userName = userName.trim();
        User user = userMapper.selectByMap(ImmutableMap.of("name", userName)).get(0);

        if (user == null) {
            throw new UsernameNotFoundException("??????????????????: " + userName);
        }

        // select roles by user_role
        setUserDetailsByUserId(user);
        return user;
    }

    /**
     * ????????????
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

        // ???????????????
        String encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        user.setId(null);
        userMapper.insert(user);

        // userRole
        UserRole userRole = new UserRole();
        userRole.setRoleId(2);
        userRole.setUserId(user.getId());
        userRoleMapper.insert(userRole);

        logger.info("???????????? name: " + user.getName() + " email: " + user.getEmail());
        logger.info(user.toString());
        return true;
    }

    /**
     * ??????????????????
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
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

    /**
     * ??????????????????????????????????????????????????????
     * @param username
     */
    private void ensureUserNameNotExist(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name", username);
        User user =  userMapper.selectOne(wrapper);
        if (user != null) {
            throw new UserAlreadyExsitException(ImmutableMap.of("username", username));
        }
    }

    /**
     * ???????????????????????????????????????
     * @param user
     * @return
     */
    private User setUserDetailsByUserId(User user) {
        List<UserRole> userRoles = userRoleMapper.selectByMap(ImmutableMap.of("user_id", user.getId()));
        logger.info("userRoles: " + userRoles);
        List<Integer> ids = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        logger.info("ids: " + ids);
        if (ids == null || ids.isEmpty()) {
            return user;
        }
        List<Role> roles = roleMapper.selectBatchIds(ids);

        user.setRoles(roles);
        return user;
    }
}
