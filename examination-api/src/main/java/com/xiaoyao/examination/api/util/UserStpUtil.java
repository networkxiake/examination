package com.xiaoyao.examination.api.util;

import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpLogic;

import java.util.Map;

public class UserStpUtil {
    private static final StpLogic userStpLogic = new StpLogicJwtForStateless("user");

    public static String login(long id, Map<String, Object> extra) {
        userStpLogic.login(id, SaLoginConfig.setExtraData(extra));
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
