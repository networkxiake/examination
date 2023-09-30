package com.xiaoyao.examination.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "examination.user")
public class ExaminationUserProperties {
    /**
     * 初始运营账户的用户名。
     */
    private String initAdminUsername;

    /**
     * 初始运营账户的密码。
     */
    private String initAdminPassword;

    /**
     * 初始运营账户的姓名。
     */
    private String initAdminName;
}
