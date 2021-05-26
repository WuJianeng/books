package org.jianeng.books.model.exception;

import com.fasterxml.jackson.databind.ser.Serializers;

import java.util.Map;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/25 10:18
 */
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(Map<String, Object> data) {
        super(ErrorCode.RESOURCE_NOT_FOUND, data);
    }
}
