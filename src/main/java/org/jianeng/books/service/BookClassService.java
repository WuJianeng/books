package org.jianeng.books.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.jianeng.books.mapper.BookClassMapper;
import org.jianeng.books.model.BookClass;
import org.jianeng.books.model.exception.RequestValidationFailedException;
import org.jianeng.books.model.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/25 21:45
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookClassService {
    private BookClassMapper bookClassMapper;

    /**
     * 根据书籍分类 id 获取该分类信息
     * @param id
     * @return
     */
    public BookClass getBookClassById(Long id) {
        BookClass bookClass = bookClassMapper.selectById(id);
        if (bookClass == null) {
            throw new ResourceNotFoundException(ImmutableMap.of("bookClass id not found", id));
        }
        return bookClass;
    }

    /**
     * 返回用户的所有书籍分类
     * @param userId
     * @return
     */
    public List<BookClass> getAllBookClassByUserId(Long userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        List<BookClass> bookClassList = bookClassMapper.selectList(wrapper);
        return bookClassList;
    }

    /**
     * 给指定用户添加书籍分类
     * @param userId
     * @param className
     * @return
     */
    public boolean addBookClass(Long userId, String className) {
        if (className == null || className.trim().isEmpty()) {
            throw new RequestValidationFailedException(ImmutableMap.of("illegal book class name", className));
        }
        className = className.trim();
        List<BookClass> bookClassList = getAllBookClassByUserId(userId);
        for (BookClass bookClass : bookClassList) {
            if (bookClass.getClassName().equals(className)) {
                throw new RequestValidationFailedException(ImmutableMap.of("illegal request, class exists.", className));
            }
        }

        BookClass bookClass = new BookClass();
        bookClass.setUserId(userId);
        bookClass.setClassName(className);

        bookClassMapper.insert(bookClass);

        return true;
    }

    /**
     * 根据书籍分类 id 更新书籍分类名称
     * @param id
     * @param className
     * @return
     */
    public boolean updateBookClass(Long id, String className) {
        if (className == null || className.trim().isEmpty()) {
            throw new RequestValidationFailedException(ImmutableMap.of("class name is illegal.", className));
        }

        className = className.trim();
        BookClass bookClass = getBookClassById(id);
        bookClass.setClassName(className);
        bookClassMapper.updateById(bookClass);

        return true;
    }

    /**
     *  删除书籍分类
     * @param id
     * @return
     */
    public boolean deleteBookClass(Long id) {
        int res = bookClassMapper.deleteById(id);
        if (res <= 0) {
            throw new RequestValidationFailedException(ImmutableMap.of("id is illegal.", id));
        } else {
            return true;
        }
    }
}
