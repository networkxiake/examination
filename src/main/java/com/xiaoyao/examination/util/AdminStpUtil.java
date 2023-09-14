package com.xiaoyao.examination.util;

import cn.dev33.satoken.stp.StpLogic;

public class AdminStpUtil {
    private static final StpLogic adminStpLogic = new StpLogic("admin");

    public static String login(long id) {
        adminStpLogic.login(id);
        return adminStpLogic.getTokenValue();
    }

    public static void checkLogin() {
        adminStpLogic.checkLogin();
    }

    public static void logout() {
        adminStpLogic.logout();
    }

    public static void logout(long id) {
        adminStpLogic.logout(id);
    }

    public static long getLoginId() {
        return adminStpLogic.getLoginIdAsLong();
    }
}
