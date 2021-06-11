package org.jianeng.books.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/11 22:14
 */
@Data
public class UserLoginParam {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
}
