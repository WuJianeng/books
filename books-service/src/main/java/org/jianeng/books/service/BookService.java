package org.jianeng.books.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ImmutableMap;
import org.jianeng.books.mapper.BookClassMapper;
import org.jianeng.books.mapper.BookMapper;
import org.jianeng.books.model.Book;
import org.jianeng.books.model.BookClass;
import org.jianeng.books.model.exception.RequestValidationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 22:24
 */

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookClassMapper bookClassMapper;

    /**
     * 根据用户 id 获取所属的所有书籍
     * @param userId
     * @return
     */
    public List<Book> getUserBooksByUserId(long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        List<Book> books = bookMapper.selectList(queryWrapper);
        return books;
    }

    /**
     * 根据用户 id 和书名返回该书
     * @param userId
     * @param bookName
     * @return
     */
    public Book getUserBookByUserIdAndBookName(long userId, String bookName) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        wrapper.eq("book_name", bookName.trim());

        List<Book> books = bookMapper.selectList(wrapper);
        if (books == null || books.size() == 0) {
            return null;
        } else {
            return books.get(0);
        }
    }

    /**
     * 根据书籍 id 返回该书籍
     * @param bookId
     * @return
     */
    public Book getBookByBookId(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }

    /**
     * 获取用户所有书籍分类
     * @param userId
     * @return
     */
    public List<BookClass> getAllBookClassesByUserId(long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        List<BookClass> bookClassList = bookClassMapper.selectList(queryWrapper);
        return bookClassList;
    }

    /**
     * 修改书籍信息
     * @return
     */
    public boolean updateUserBookByBookId(Book book) {
        Book oldBook = getBookByBookId(book.getId());
        if (!oldBook.getUserId().equals(book.getUserId())) {
            throw new RequestValidationFailedException(ImmutableMap.of("无权更改他人信息.", book.getId()));
        }

        int res = bookMapper.updateById(book);
        if (res <= 0) {
            throw new RequestValidationFailedException(ImmutableMap.of("update fail.", book));
        }
        return true;
    }

    /**
     * 添加书籍
     * @param book
     * @return
     */
    public boolean addUserBook(Book book) {
        if (book.getUserId() == null || book.getBookName() == null || book.getBookName().trim().isEmpty()) {
            throw new RequestValidationFailedException(ImmutableMap.of("user id", book.getUserId(), "book name",
                    book.getBookName()));
        }

        Book old = getUserBookByUserIdAndBookName(book.getUserId(), book.getBookName());
        if (old != null) {
            throw new RequestValidationFailedException(ImmutableMap.of("book exist.", old));
        }

        book.setId(null);
        int res = bookMapper.insert(book);
        if (res <= 0) {
            throw new RequestValidationFailedException(ImmutableMap.of("request fail.", book));
        }
        return true;
    }

    /**
     * 删除书籍
     * @param bookId
     * @return
     */
    public boolean deleteUserBook(Long bookId) {
        int res = bookMapper.deleteById(bookId);
        if (res <= 0) {
            throw new RequestValidationFailedException(ImmutableMap.of("book may not exist.", bookId));
        }
        return true;
    }
}
