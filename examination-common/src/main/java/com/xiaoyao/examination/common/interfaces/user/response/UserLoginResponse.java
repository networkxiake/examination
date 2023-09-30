package com.xiaoyao.examination.common.interfaces.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserLoginResponse implements Serializable {
    private long id;
    private String name;
    private String photo;
}
