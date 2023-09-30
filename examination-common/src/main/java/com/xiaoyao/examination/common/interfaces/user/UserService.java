package com.xiaoyao.examination.common.interfaces.user;

import com.xiaoyao.examination.common.interfaces.user.response.ApplyUploadPhotoResponse;
import com.xiaoyao.examination.common.interfaces.user.response.UserLoginResponse;
import com.xiaoyao.examination.common.interfaces.user.response.UserProfileResponse;

public interface UserService {
    void sendVerificationCode(String ip, String phone);

    UserLoginResponse login(String phone, String code);

    ApplyUploadPhotoResponse applyUploadPhoto(String suffix);

    String confirmPhoto(long userId, String path);

    UserProfileResponse profile(long userId);

    void updateProfile(long userId, String name, String gender);

    void updatePhone(long loginId, String phone, String code);
}
