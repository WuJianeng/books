package org.jianeng.books.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/7 10:46
 */
@Data
@TableName("role")
public class Role implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 角色名称， ROLE_xxx
     */
    @Value("role_name")
    private String roleName;
    /**
     * 角色名称，中文
     */
    @Value("name")
    private String name;
}
