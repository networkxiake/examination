package com.xiaoyao.examination.common.exception;

public enum ErrorCode {
    ERROR(1000, "服务器异常"),
    USERNAME_OR_PASSWORD_ERROR(1001, "用户名或密码错误"),
    INVALID_PARAMS(1002, "非法参数"),
    NOT_LOGIN(1003, "未登录"),
    OLD_PASSWORD_ERROR(1004, "原密码错误"),
    OLD_AND_NEW_PASSWORD_SAME(1005, "新密码不能和旧密码相同"),
    NEED_INIT_ADMIN(1006, "需要初始的管理员账户"),
    USERNAME_EXIST(1007, "用户名已存在"),
    GOODS_TYPE_NOT_EXIST(1008, "套餐类型不存在"),
    GOODS_STATUS_NOT_EXIST(1009, "套餐状态不存在"),
    GOODS_CODE_EXIST(1010, "套餐编号已存在"),
    DISCOUNT_NOT_EXIST(1011, "折扣类别不存在"),
    GOODS_NOT_EXIST(1012, "套餐不存在"),
    GOODS_STATUS_NOT_ALLOW_UPDATE(1013, "套餐状态不允许修改，请先下架套餐！"),
    GOODS_CAN_NOT_DELETE(1014, "只有已下架的套餐才能删除"),
    VERIFICATION_CODE_SEND_TOO_FREQUENTLY(1015, "验证码发送过于频繁"),
    VERIFICATION_CODE_NOT_EXIST(1016, "验证码不存在"),
    VERIFICATION_CODE_ERROR(1017, "验证码错误"),
    USER_ALREADY_EXIST(1018, "用户已存在"),
    PHOTO_TYPE_ERROR(1019, "图片的类型要为jpg、png或jpeg"),
    GOODS_SORT_NOT_EXIST(1020, "套餐分类不存在"),
    DISCOUNT_SCRIPT_ERROR(1021, "折扣计算脚本语法错误"),
    ORDER_STATUS_ERROR(1022, "订单状态错误"),
    GOODS_NOT_FOUND(1023, "套餐不存在"),
    STORAGE_SPACE_NOT_EXIST(1024, "存储空间不存在，请先申请空间！"),
    STORAGE_SPACE_HAVE_NO_FILE(1025, "存储空间中没有文件，请先上传文件！"),
    IMAGE_CODE_NOT_EXIST(1026, "图形验证码不存在"),
    IMAGE_CODE_ERROR(1027, "图形验证码错误"),
    ORDER_CREATED(1028, "订单已创建，请刷新！"),
    ORDER_NOT_FOUND(1029, "订单不存在"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
