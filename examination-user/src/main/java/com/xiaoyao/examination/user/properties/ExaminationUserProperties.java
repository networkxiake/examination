package com.xiaoyao.examination.user.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

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

    public String getInitAdminUsername() {
        return initAdminUsername;
    }

    public void setInitAdminUsername(String initAdminUsername) {
        this.initAdminUsername = initAdminUsername;
    }

    public String getInitAdminPassword() {
        return initAdminPassword;
    }

    public void setInitAdminPassword(String initAdminPassword) {
        this.initAdminPassword = initAdminPassword;
    }

    public String getInitAdminName() {
        return initAdminName;
    }

    public void setInitAdminName(String initAdminName) {
        this.initAdminName = initAdminName;
    }
}
