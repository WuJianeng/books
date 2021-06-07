package org.jianeng.books.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 21:44
 */

@Data
@TableName("book")
public class Book {
    @TableId(type = IdType.AUTO)
    private Long id;

    @Value("user_id")
    private Long userId;

    @Value("address_id")
    private Long addressId;

    @Value("book_class_id")
    private Long bookClassId;

    @Value("book_name")
    private String bookName;

    @Value("read_page")
    private int readPage;
}
