package org.jianeng.books.exception;

import org.jianeng.books.model.exception.BaseException;
import org.jianeng.books.model.exception.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wu_jianeng@foxmail.com
 * @date 2021/5/24 12:04
 */

@ControllerAdvice(basePackages = "org.jianeng.books.controller")
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(BaseException e, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(e, request.getRequestURI());
        return new ResponseEntity<>(response, new HttpHeaders(), e.getError().getStatus());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> otherExceptionHandler(RuntimeException e, HttpServletRequest request) {
        return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
    }
}
