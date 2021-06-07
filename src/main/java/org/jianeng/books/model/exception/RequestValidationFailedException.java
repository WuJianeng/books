package org.jianeng.books.model.exception;

import java.util.Map;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/25 11:12
 */
public class RequestValidationFailedException extends BaseException {
    public RequestValidationFailedException(Map<String, Object> data) {
        super(ErrorCode.REQUEST_VALIDATION_FAILED, data);
    }
}
