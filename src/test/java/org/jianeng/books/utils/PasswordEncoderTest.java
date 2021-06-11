package org.jianeng.books.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/11 18:48
 */
@SpringBootTest
@Slf4j
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder encoder;

    private Logger logger = LoggerFactory.getLogger(PasswordEncoderTest.class);

    private static final String SEQUENCE = "books123456";

    @Test
    public void testEncode() {
        // encode 123456
        String encoded = encoder.encode(SEQUENCE);
        logger.info("encoded: " + encoded);

        Assert.assertTrue(encoder.matches(SEQUENCE, encoded));
    }
}
