package com.xiaoyao.examination.util;

import cn.dev33.satoken.stp.StpLogic;

public class UserStpUtil {
    private static final StpLogic userStpLogic = new StpLogic("user");

    public static String login(long id) {
        userStpLogic.login(id);
        return userStpLogic.getTokenValue();
    }

    public static void checkLogin() {
        userStpLogic.checkLogin();
    }

    public static void logout() {
        userStpLogic.logout();
    }

    public static long getLoginId() {
        return userStpLogic.getLoginIdAsLong();
    }
}
