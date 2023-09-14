package com.xiaoyao.examination.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseBody<T> {
    private final int code;
    private final String message;
    private final T data;
}
