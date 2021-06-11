package org.jianeng.books.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/7 11:10\
 * 用户和角色关联表
 */
@Data
@TableName("user_role")
public class UserRole {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Value("user_id")
    private Integer userId;

    @Value("role_id")
    private Integer roleId;
}
