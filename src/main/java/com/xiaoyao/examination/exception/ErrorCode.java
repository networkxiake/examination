package com.xiaoyao.examination.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
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
    GOODS_NAME_OR_CODE_EXIST(1010, "套餐名称或编号已存在"),
    DISCOUNT_NOT_EXIST(1011, "折扣类别不存在"),
    GOODS_NOT_EXIST(1012, "套餐不存在"),
    GOODS_STATUS_NOT_ALLOW_UPDATE(1013, "套餐状态不允许修改"),
    ;

    private final int code;
    private final String message;
}
