package org.jianeng.books.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jianeng.books.model.Book;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 22:22
 */

@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
