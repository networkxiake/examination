package com.xiaoyao.examination.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.xiaoyao.examination.response.ResponseBody;
import com.xiaoyao.examination.response.ResponseBodyBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理认证异常
     */
    @ExceptionHandler(NotLoginException.class)
    public ResponseBody<Object> handleNotLoginException(NotLoginException e) {
        return ResponseBodyBuilder.build(ErrorCode.NOT_LOGIN);
    }

    /**
     * 处理表单参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseBody<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        if (log.isInfoEnabled()) {
            FieldError fieldError = e.getFieldError();
            log.info("{}字段{}", Objects.requireNonNull(fieldError).getField(), fieldError.getDefaultMessage());
        }
        return ResponseBodyBuilder.build(ErrorCode.INVALID_PARAMS);
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(ExaminationException.class)
    public ResponseBody<Object> handleExaminationException(ExaminationException e) {
        log.error("服务器异常", e);
        return ResponseBodyBuilder.build(e.getCode(), e.getMessage());
    }

    /**
     * 处理未知异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseBody<Object> handleException(Exception e) {
        log.error("服务器异常", e);
        return ResponseBodyBuilder.build(ErrorCode.ERROR);
    }
}
