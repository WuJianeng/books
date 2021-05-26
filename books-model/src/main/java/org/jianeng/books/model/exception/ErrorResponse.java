package org.jianeng.books.model.exception;

import lombok.Data;
import org.jianeng.books.model.exception.BaseException;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/14 17:00
 */
@Data
public class ErrorResponse {
    private int code;

    private int status;

    private String message;

    private String path;

    private Instant timestamp;

    private HashMap<String, Object> data = new HashMap<>();

    public ErrorResponse() {
    }

    public ErrorResponse(BaseException ex, String path) {
        this(ex.getError().getCode(), ex.getError().getStatus().value(), ex.getError().getMessage(), path, ex.getData());
    }

    public ErrorResponse(int code, int status, String message, String path, Map<String, Object> data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }


}
