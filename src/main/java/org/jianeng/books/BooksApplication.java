package org.jianeng.books;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/14 16:17
 */
@SpringBootApplication
@MapperScan(basePackages = "org.jianeng.books.mapper")
public class BooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
    }

}
