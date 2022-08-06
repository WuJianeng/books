package org.jianeng.books.dto;

import lombok.Data;
import org.jianeng.books.model.Book;
import org.jianeng.books.model.BookClass;
import org.jianeng.books.model.UserAddress;

import javax.validation.constraints.NotNull;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/14 10:28
 */
@Data
public class BookInfo {
    private Integer id;

    private Integer userId;

    private Integer addressId;

    private String address;

    private Integer bookClassId;

    private String bookClass;

    private String bookName;

    private String authorName;

    private Integer readPage;

    private Integer wholePage;

    private String comment;

    public static BookInfo from(@NotNull Book book, BookClass bookClass, UserAddress address) {
        BookInfo info = new BookInfo();
        info.setId(book.getId());
        info.setUserId(book.getUserId());
        info.setBookName(book.getBookName());
        info.setAuthorName(book.getAuthorName());
        info.setAddressId(book.getAddressId());
        info.setBookClassId(book.getBookClassId());
        info.setReadPage(book.getReadPage());
        info.setWholePage(book.getWholePage());
        info.setComment(book.getComment());

        if (bookClass != null) {
            info.setBookClass(bookClass.getClassName());
        }

        if (address != null) {
            info.setAddress(address.getAddress());
        }
        return info;
    }
}
