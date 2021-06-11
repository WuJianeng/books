package org.jianeng.books.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 21:50
 */

@Data
public class UserAddress {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Value("user_id")
    private Integer userId;

    @Value("address")
    private String address;
}
