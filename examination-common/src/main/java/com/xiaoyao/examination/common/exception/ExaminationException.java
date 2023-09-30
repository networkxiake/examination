package com.xiaoyao.examination.common.exception;

public class ExaminationException extends RuntimeException {
    private final int code;

    /**
     * 服务调用方反序列化时使用的构造器。
     */
    public ExaminationException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 抛出异常时为了方便而使用的构造器。
     */
    public ExaminationException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }

    public int getCode() {
        return code;
    }
}
