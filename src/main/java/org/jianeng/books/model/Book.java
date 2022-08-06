package org.jianeng.books.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 21:44
 */

@Data
@TableName("book")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Value("user_id")
    private Integer userId;

    @Value("address_id")
    private Integer addressId;

    @Value("book_class_id")
    private Integer bookClassId;

    @Value("book_name")
    private String bookName;

    @Value("author_name")
    private String authorName;

    @Value("read_page")
    private Integer readPage;

    @Value("whole_page")
    private Integer wholePage;

    @Value("comment")
    private String comment;

    @Value("buy_time")
    private Long buyTime;

    @Value("read_time")
    private Long readTime;

    @Value(("track_time"))
    private Long trackTime;
}
