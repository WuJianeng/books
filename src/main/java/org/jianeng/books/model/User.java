package org.jianeng.books.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 15:39
 */
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String password;
    private String gender;
    private Integer age;
    private String email;
}
