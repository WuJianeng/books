package org.jianeng.books.controller;

import org.jianeng.books.dto.BookInfo;
import org.jianeng.books.model.Book;
import org.jianeng.books.model.BookClass;
import org.jianeng.books.service.BookClassService;
import org.jianeng.books.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 22:28
 */
@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookClassService bookClassService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping("/book/all/{user_id}")
    public ResponseEntity<List<Book>> getAllBookByUserId(@PathVariable("user_id") Integer userId) {
        logger.debug("user id: " + userId);
        List<Book> books = bookService.getBookListByUserId(userId);
        logger.debug("books num: " + books.size());
        return ResponseEntity.ok(books);
    }

    /**
     * 返回用户所有的 BookInfo
     * @param userId
     * @return
     */
    @GetMapping("/bookinfo/all/{user_id}")
    public ResponseEntity<List<BookInfo>> getBookInfoListByUserId(@PathVariable("user_id")Integer userId) {
        List<BookInfo> bookInfoList = bookService.getBookInfoListByUserId(userId);
        return ResponseEntity.ok(bookInfoList);
    }

    /**
     * 根据 Book 中的筛选条件返回所有符合条件的 BookInfo
     * @param book
     * @return
     */
    @PostMapping("/bookinfo/all")
    public ResponseEntity<List<BookInfo>> getBookInfoListByBook(@RequestBody Book book) {
        logger.info("book: " + book.toString());

        List<BookInfo> bookInfoList = bookService.getBookInfoListByBook(book);
        return ResponseEntity.ok(bookInfoList);
    }

    /**
     * 获取用户所有的书籍分类
     * @param userId
     * @return
     */
    @GetMapping("/class/all/{user_id}")
    public ResponseEntity<List<BookClass>> getAllBookClassByUserId(@PathVariable("user_id")Integer userId) {
        List<BookClass> bookClassList = bookService.getAllBookClassesByUserId(userId);
        return ResponseEntity.ok(bookClassList);
    }

    /**
     * 根据 BookClass 获取符合要求的 BookClass, 支持模糊查询
     * @param bookClass
     * @return
     */
    @PostMapping("/class/all")
    public ResponseEntity<List<BookClass>> getBookClassListByBookClass(@RequestBody BookClass bookClass) {
        List<BookClass> bookClassList = bookService.getBookClassListByBookClass(bookClass);
        return ResponseEntity.ok(bookClassList);
    }

    /**
     * 根据书籍id查找书籍
     * @param bookId
     * @return
     */
    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookByBookId(@PathVariable("id")Integer bookId) {
        Book book = bookService.getBookByBookId(bookId);
        return ResponseEntity.ok(book);
    }

    /**
     * 根据书籍分类id查找书籍分类
     * @param bookClassId
     * @return
     */
    @GetMapping("/class/{id}")
    public ResponseEntity<BookClass> getBookClassByBookClassId(@PathVariable("id")Integer bookClassId) {
        BookClass bookClass = bookClassService.getBookClassById(bookClassId);
        return ResponseEntity.ok(bookClass);
    }

    /**
     * 添加书籍
     * @param book
     * @return
     */
    @PostMapping("/book/add")
    public ResponseEntity<Boolean> addBookByUserId(@RequestBody Book book) {
        if (book.getReadPage() == null) {
            book.setReadPage(0);
        }
        boolean res = bookService.addUserBook(book);
        return ResponseEntity.ok(res);
    }

    /**
     * 删除书籍
     * @param id
     * @return
     */
    @PostMapping("/book/delete/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable Integer id) {
        boolean res = bookService.deleteUserBook(id);
        return ResponseEntity.ok(res);
    }

    /**
     * 更新书籍信息
     * @param book
     * @return
     */
    @PostMapping("/book/update")
    public ResponseEntity<Boolean> updateBook(@RequestBody Book book) {
        boolean res = bookService.updateUserBookByBookId(book);
        return ResponseEntity.ok(res);
    }

    /**
     * 添加书籍分类
     * @param bookClass
     * @return
     */
    @PostMapping("/class/add")
    public ResponseEntity<Boolean> addBookClass(@RequestBody BookClass bookClass) {
        boolean res = bookClassService.addBookClass(bookClass.getUserId(), bookClass.getClassName());
        return ResponseEntity.ok(res);
    }

    /**
     * 删除书籍分类
     * @param id
     * @return
     */
    @PostMapping("/class/delete/{id}")
    public ResponseEntity<Boolean> deleteBookClass(@PathVariable("id")Integer id) {
        boolean res = bookClassService.deleteBookClass(id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/class/update")
    public ResponseEntity<Boolean> updateBookClassName(@RequestBody BookClass bookClass) {
        boolean res = bookClassService.updateBookClass(bookClass.getId(), bookClass.getClassName());
        return ResponseEntity.ok(res);
    }
}
