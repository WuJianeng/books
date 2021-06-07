package org.jianeng.books.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 14:31
 */

@RestController
@RequestMapping("/hello")
@CrossOrigin
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        logger.debug("hello world.");
        return ResponseEntity.ok("hello, world!");
    }
}
