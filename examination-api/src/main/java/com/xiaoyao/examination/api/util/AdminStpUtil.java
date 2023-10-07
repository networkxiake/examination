package com.xiaoyao.examination.api.util;

import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpLogic;
import com.xiaoyao.examination.common.exception.ErrorCode;
import com.xiaoyao.examination.common.exception.ExaminationException;

import java.util.Map;

public class AdminStpUtil {
    private static final StpLogic adminStpLogic = new StpLogicJwtForStateless("admin");

    public static String login(long id, Map<String, Object> extra) {
        adminStpLogic.login(id, SaLoginConfig.setExtraData(extra));
        return adminStpLogic.getTokenValue();
    }

    public static void checkLogin() {
        adminStpLogic.checkLogin();
    }

    public static void checkLoginInit() {
        checkLogin();
        if (!(boolean) adminStpLogic.getExtra("isInitAdmin")) {
            throw new ExaminationException(ErrorCode.NEED_INIT_ADMIN);
        }
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
