package org.jianeng.books.controller;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jianeng.books.dto.UserInfo;
import org.jianeng.books.dto.UserLoginParam;
import org.jianeng.books.model.User;
import org.jianeng.books.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 16:16
 */

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @PostMapping("/login")
    public ResponseEntity<Map> login(@RequestBody UserLoginParam userLoginParam) {
        String username = userLoginParam.getUsername();
        String password = userLoginParam.getPassword();

        String token = userService.login(username, password);

        return ResponseEntity.ok(ImmutableMap.of("token", token, "tokenHead", tokenHead));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> queryUserById(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        System.out.println(user.toString());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<User> queryUserByUserName(@RequestParam String username) {
        User user = userService.getUserByUserName(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> registerUser(@RequestParam("name")String userName,
                                                @RequestParam("password")String password,
                                                @RequestParam("email")String email,
                                                @Param("gender")String gender,
                                                @Param("age")Integer age) {
        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setGender(gender);
        user.setAge(age);
        boolean res = userService.addUser(user);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateUserInfo(@RequestBody User user) {
       boolean res = userService.updateUser(user);
       return ResponseEntity.ok(res);
    }
}
