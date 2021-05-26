package org.jianeng.books;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "org.jianeng.books.mapper")
public class BooksWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksWebApplication.class, args);
	}

}
