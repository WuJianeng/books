package org.jianeng.books.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/7 16:26
 */
@SpringBootTest
@Slf4j
public class BCryptPasswordEncoderTest {

    private Logger logger = LoggerFactory.getLogger(BCryptPasswordEncoder.class);

    private static final String RAW_STRING = "hello word";

    @Test
    public void test() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        // encode
        String encode = encoder.encode(RAW_STRING);
        logger.info(encode);

        Assert.assertTrue(encoder.matches(RAW_STRING, encode));
    }
}
