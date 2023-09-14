package com.xiaoyao.examination.response;

import com.xiaoyao.examination.exception.ErrorCode;

public class ResponseBodyBuilder {
    public static <T> ResponseBody<T> build() {
        return new ResponseBody<>(200, "success", null);
    }

    public static <T> ResponseBody<T> build(T data) {
        return new ResponseBody<>(200, "success", data);
    }

    public static <T> ResponseBody<T> build(int code, String message) {
        return new ResponseBody<>(code, message, null);
    }

    public static <T> ResponseBody<T> build(ErrorCode errorCode) {
        return new ResponseBody<>(errorCode.getCode(), errorCode.getMessage(), null);
    }
}
