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
    GOODS_STATUS_NOT_ALLOW_UPDATE(1013, "套餐状态不允许修改，请先下架套餐！"),
    GOODS_CAN_NOT_DELETE(1014, "只有销量为零和已下架的套餐才能删除"),
    VERIFICATION_CODE_SEND_TOO_FREQUENTLY(1015, "验证码发送过于频繁"),
    VERIFICATION_CODE_NOT_EXIST(1016, "验证码不存在"),
    VERIFICATION_CODE_ERROR(1017, "验证码错误"),
    USER_ALREADY_EXIST(1018, "用户已存在"),
    PHOTO_TYPE_ERROR(1019, "图片的类型要为jpg、png或jpeg"),
    GOODS_SORT_NOT_EXIST(1020, "套餐分类不存在");

    private final int code;
    private final String message;
}
