package com.xiaoyao.examination.api.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.xiaoyao.examination.api.response.ResponseBody;
import com.xiaoyao.examination.api.response.ResponseBodyBuilder;
import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseBody<Object> handleMethodArgumentNotValidException(Exception e) {
        String message = null;
        if (e instanceof MethodArgumentNotValidException exception) {
            FieldError fieldError = exception.getFieldError();
            message = Objects.requireNonNull(fieldError).getField() + "字段" + fieldError.getDefaultMessage();
        } else if (e instanceof ConstraintViolationException exception) {
            ConstraintViolation<?> violation = exception.getConstraintViolations().iterator().next();
            String path = violation.getPropertyPath().toString();
            message = path.substring(path.indexOf(".") + 1) + "字段" + violation.getMessage();
        }
        return ResponseBodyBuilder.build(ErrorCode.INVALID_PARAMS.getCode(), message);
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
