package org.jianeng.books.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 21:50
 */

@Data
public class UserAddress {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String address;
}