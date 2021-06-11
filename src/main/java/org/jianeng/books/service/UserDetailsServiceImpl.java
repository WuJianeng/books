package org.jianeng.books.service;

import org.intellij.lang.annotations.Language;
import org.jianeng.books.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/7 17:38
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Lazy
    UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("执行自定义登录逻辑");

        User user = userService.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}
