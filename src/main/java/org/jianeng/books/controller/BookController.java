package org.jianeng.books.controller;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<Book>> getAllBookByUserId(@PathVariable("user_id") Long userId) {
        logger.debug("user id: " + userId);
        List<Book> books = bookService.getUserBooksByUserId(userId);
        logger.debug("books num: " + books.size());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/class/all/{user_id}")
    public ResponseEntity<List<BookClass>> getAllBookClassByUserId(@PathVariable("user_id")Long userId) {
        List<BookClass> bookClassList = bookService.getAllBookClassesByUserId(userId);
        return ResponseEntity.ok(bookClassList);
    }

    /**
     * 根据书籍id查找书籍
     * @param bookId
     * @return
     */
    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookByBookId(@PathVariable("id")Long bookId) {
        Book book = bookService.getBookByBookId(bookId);
        return ResponseEntity.ok(book);
    }

    /**
     * 根据书籍分类id查找书籍分类
     * @param bookClassId
     * @return
     */
    @GetMapping("/class/{id}")
    public ResponseEntity<BookClass> getBookClassByBookClassId(@PathVariable("id")Long bookClassId) {
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
        boolean res = bookService.addUserBook(book);
        return ResponseEntity.ok(res);
    }

    /**
     * 删除书籍
     * @param id
     * @return
     */
    @PostMapping("/book/delete/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable Long id) {
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
     * @param userId
     * @param bookName
     * @return
     */
    @PostMapping("/class/add")
    public ResponseEntity<Boolean> addBookClass(@RequestParam("user_id")Long userId, @RequestParam("book_name")String bookName) {
        boolean res = bookClassService.addBookClass(userId, bookName);
        return ResponseEntity.ok(res);
    }

    /**
     * 删除书籍分类
     * @param id
     * @return
     */
    @PostMapping("/class/delete/{id}")
    public ResponseEntity<Boolean> deleteBookClass(@PathVariable("id")Long id) {
        boolean res = bookClassService.deleteBookClass(id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/class/update/{book_id}")
    public ResponseEntity<Boolean> updateBookClassName(@PathVariable("book_id")Long bookId, @RequestParam String class_name) {
        boolean res = bookClassService.updateBookClass(bookId, class_name);
        return ResponseEntity.ok(res);
    }
}
