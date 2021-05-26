package org.jianeng.books.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jianeng.books.model.User;
import org.jianeng.books.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 16:16
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<User> queryUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        System.out.println(user.toString());
        return ResponseEntity.ok(user);
    }

    @GetMapping("")
    public ResponseEntity<User> queryUserByUserName(@RequestParam("name")String username) {
        User user = userService.getUserByUserName(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> registerUser(@RequestParam("name")String userName,
                                                @RequestParam("email")String email,
                                                @Param("gender")String gender,
                                                @Param("age")Integer age) {
        User user = new User();
        user.setName(userName);
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
