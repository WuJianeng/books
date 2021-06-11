package org.jianeng.books.service;

import lombok.extern.slf4j.Slf4j;
import org.jianeng.books.model.Book;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/7 17:00
 */
@SpringBootTest
@Slf4j
class BookServiceTest {

    @Autowired
    BookService bookService;

    Logger logger = LoggerFactory.getLogger(BookServiceTest.class);

    @Test
    @Transactional
    @Rollback
    void getUserBooksByUserId() {
        Assert.assertNotNull(bookService);

        List<Book> books = bookService.getUserBooksByUserId(1);
        Assert.assertNotNull(books);
        logger.info(books.get(0).toString());
        Assert.assertTrue(books.size()>0);
    }
}