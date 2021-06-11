package org.jianeng.books;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/14 16:17
 */
@SpringBootTest
@MapperScan(basePackages = "org.jianeng.books.mapper")
class BooksApplicationTests {

    @Test
    void contextLoads() {
    }

}
