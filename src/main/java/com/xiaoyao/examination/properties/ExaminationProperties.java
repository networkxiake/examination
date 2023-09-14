package com.xiaoyao.examination.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "examination")
public class ExaminationProperties {
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
