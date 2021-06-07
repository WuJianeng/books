package org.jianeng.books.model.exception;

import java.util.Map;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/6/5 15:38
 */
public class UserAlreadyExsitException extends BaseException {
    public UserAlreadyExsitException(Map<String, Object> data) {
        super(ErrorCode.USER_NAME_ALREADY_EXIST, data);
    }
}
