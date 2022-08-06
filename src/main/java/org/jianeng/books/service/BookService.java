package org.jianeng.books.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jianeng.books.dto.BookInfo;
import org.jianeng.books.dto.MoveBooks;
import org.jianeng.books.mapper.BookClassMapper;
import org.jianeng.books.mapper.BookMapper;
import org.jianeng.books.model.Book;
import org.jianeng.books.model.BookClass;
import org.jianeng.books.model.UserAddress;
import org.jianeng.books.model.exception.RequestValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 22:24
 */

@Service
@Slf4j
public class BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookClassMapper bookClassMapper;
    @Autowired
    private UserAddressService userAddressService;
    private Logger logger = LoggerFactory.getLogger(BookService.class);

    /**
     * 根据用户 id 获取所属的所有书籍
     * @param userId
     * @return
     */
    public List<Book> getBookListByUserId(int userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        List<Book> books = bookMapper.selectList(queryWrapper);
        return books;
    }

    /**
     * 根据用户 id 获取该用户的所有书籍详细信息
     * @param userId
     * @return
     */
    public List<BookInfo> getBookInfoListByUserId(int userId) {
        List<Book> books = getBookListByUserId(userId);
        List<BookInfo> infoList = getBookInfoListByBookList(books);

        return infoList;
    }

    @NotNull
    private List<BookInfo> getBookInfoListByBookList(List<Book> books) {
        List<BookInfo> infoList = new ArrayList<>();
        for (Book book : books) {
            // 获取每本书的详细信息
            BookInfo info = getBookInfoByBook(book);
            infoList.add(info);
        }
        return infoList;
    }

    @NotNull
    private BookInfo getBookInfoByBook(Book book) {
        UserAddress address = userAddressService.getUserAddressByAddressId(book.getAddressId());
        BookClass bookClass = getBookClassByBookClassId(book.getBookClassId());
        BookInfo info = BookInfo.from(book, bookClass, address);
        return info;
    }

    /**
     * 根据 Book 中的筛选条件获取书籍的详细信息
     * @param book
     * @return
     */
    public List<BookInfo> getBookInfoListByBook(Book book) {
        List<Book> books = getBookByBookWrapper(book);
        List<BookInfo> bookInfoList = getBookInfoListByBookList(books);

        return bookInfoList;
    }

    public BookClass getBookClassByBookClassId(int bookClassId) {
        return bookClassMapper.selectById(bookClassId);
    }

    /**
     * 根据用户 id 和书名返回该书
     * @param userId
     * @param bookName
     * @return
     */
    public Book getBookByUserIdAndBookName(int userId, String bookName) {
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
     * 支持书籍名称模糊查询
     * @param book
     * @return
     */
    public List<Book> getBookByBookWrapper(Book book) {
        String bookName = book.getBookName();

        book.setBookName(null);
        QueryWrapper<Book> wrapper = new QueryWrapper<>(book);
        if (bookName != null && !bookName.trim().equals("")) {
            wrapper.like("book_name", bookName);
        }
        List<Book> bookList = bookMapper.selectList(wrapper);

        return bookList;
    }

    /**
     * 根据书籍 id 返回该书籍
     * @param bookId
     * @return
     */
    public Book getBookByBookId(Integer bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }

    /**
     * 获取用户所有书籍分类
     * @param userId
     * @return
     */
    public List<BookClass> getAllBookClassesByUserId(int userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        List<BookClass> bookClassList = bookClassMapper.selectList(queryWrapper);
        return bookClassList;
    }

    /**
     * 根据 BookClass 获取符合要求的 BookClass, 支持模糊查询
     * @param bookClass
     * @return
     */
    public List<BookClass> getBookClassListByBookClass(BookClass bookClass) {
        String className = bookClass.getClassName();

        bookClass.setClassName(null);
        QueryWrapper<BookClass> wrapper = new QueryWrapper<>(bookClass);
        if (className != null && !className.trim().equals("")) {
            wrapper.like("class_name", className);
        }

        return bookClassMapper.selectList(wrapper);
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

        book.setTrackTime(System.currentTimeMillis());
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

        Book old = getBookByUserIdAndBookName(book.getUserId(), book.getBookName());
        if (old != null) {
            throw new RequestValidationFailedException(ImmutableMap.of("book exist.", old));
        }

        book.setId(null);
        book.setTrackTime(System.currentTimeMillis());
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
    public boolean deleteUserBook(Integer bookId) {
        int res = bookMapper.deleteById(bookId);
        if (res <= 0) {
            throw new RequestValidationFailedException(ImmutableMap.of("book may not exist.", bookId));
        }
        return true;
    }
}
