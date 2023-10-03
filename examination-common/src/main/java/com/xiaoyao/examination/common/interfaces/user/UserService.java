package com.xiaoyao.examination.common.interfaces.user;

import com.xiaoyao.examination.common.interfaces.user.response.ApplyUploadPhotoResponse;
import com.xiaoyao.examination.common.interfaces.user.response.GenerateImageCodeResponse;
import com.xiaoyao.examination.common.interfaces.user.response.UserLoginResponse;
import com.xiaoyao.examination.common.interfaces.user.response.UserProfileResponse;

public interface UserService {
    /**
     * 生成图片验证码，默认的宽高为200x100，返回图片的Base64字符串。
     */
    GenerateImageCodeResponse generateImageCode(Integer width, Integer height);

    /**
     * 在验证图像验证码成功后，发送短信验证码。
     */
    void sendVerificationCode(String ip, String key, String code, String phone);

    /**
     * 在验证短信验证码成功后登录。
     */
    UserLoginResponse login(String phone, String code);

    ApplyUploadPhotoResponse applyUploadPhoto(String suffix);

    String confirmPhoto(long userId, String path);

    UserProfileResponse profile(long userId);

    void updateProfile(long userId, String name, String gender);

    void updatePhone(long loginId, String phone, String code);
}
