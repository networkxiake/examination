package com.xiaoyao.examination.exception;

import lombok.Getter;

@Getter
public class ExaminationException extends RuntimeException {
    private final int code;

    public ExaminationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
