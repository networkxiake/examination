package com.xiaoyao.examination.controller.form.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SendCodeForm {
    @NotBlank
    @Pattern(regexp = "^((13[0-9])|(14(0|[5-7]|9))|(15([0-3]|[5-9]))|(16(2|[5-7]))|(17[0-8])|(18[0-9])|(19([0-3]|[5-9])))\\d{8}$")
    private String phone;
}
