package com.xiaoyao.examination.common.interfaces.user.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminLoginResponse implements Serializable {
    private Long id;
    private String name;
    private String photo;
}
