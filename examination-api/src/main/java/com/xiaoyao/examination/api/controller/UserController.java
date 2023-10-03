package com.xiaoyao.examination.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xiaoyao.examination.api.annotation.CheckLoginUser;
import com.xiaoyao.examination.api.controller.dto.user.ApplyUploadPhotoDTO;
import com.xiaoyao.examination.api.controller.dto.user.GenerateImageCodeDTO;
import com.xiaoyao.examination.api.controller.dto.user.UserLoginDTO;
import com.xiaoyao.examination.api.controller.dto.user.UserProfileDTO;
import com.xiaoyao.examination.api.response.ResponseBody;
import com.xiaoyao.examination.api.response.ResponseBodyBuilder;
import com.xiaoyao.examination.api.util.UserStpUtil;
import com.xiaoyao.examination.common.interfaces.user.UserService;
import com.xiaoyao.examination.common.interfaces.user.response.ApplyUploadPhotoResponse;
import com.xiaoyao.examination.common.interfaces.user.response.UserLoginResponse;
import com.xiaoyao.examination.common.interfaces.user.response.UserProfileResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    @DubboReference
    private UserService userService;

    @GetMapping("/generate-image-code")
    public ResponseBody<GenerateImageCodeDTO> generateImageCode(Integer width, Integer height) {
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(userService.generateImageCode(width, height), GenerateImageCodeDTO.class));
    }

    @PostMapping("/send-verification-code")
    public ResponseBody<Void> sendVerificationCode(@NotBlank
                                                   @Pattern(regexp = "^((13[0-9])|(14(0|[5-7]|9))|(15([0-3]|[5-9]))|(16(2|[5-7]))|(17[0-8])|(18[0-9])|(19([0-3]|[5-9])))\\d{8}$")
                                                   String phone,
                                                   @NotBlank String key,
                                                   @NotBlank String code,
                                                   HttpServletRequest request) {
        userService.sendVerificationCode(request.getRemoteAddr(), key, code, phone);
        return ResponseBodyBuilder.build();
    }

    @PostMapping("/login")
    public ResponseBody<UserLoginDTO> login(@NotBlank
                                            @Pattern(regexp = "^((13[0-9])|(14(0|[5-7]|9))|(15([0-3]|[5-9]))|(16(2|[5-7]))|(17[0-8])|(18[0-9])|(19([0-3]|[5-9])))\\d{8}$")
                                            String phone,
                                            @NotBlank @Size(min = 6, max = 6) String code) {
        UserLoginResponse response = userService.login(phone, code);
        return ResponseBodyBuilder.build(new UserLoginDTO(UserStpUtil.login(response.getId()),
                response.getName(), response.getPhoto()));
    }

    @CheckLoginUser
    @PostMapping("/logout")
    public ResponseBody<Void> logout() {
        UserStpUtil.logout();
        return ResponseBodyBuilder.build();
    }

    @CheckLoginUser
    @PostMapping("/apply-upload-photo")
    public ResponseBody<ApplyUploadPhotoDTO> applyUploadPhoto(@NotBlank String suffix) {
        ApplyUploadPhotoResponse response = userService.applyUploadPhoto(suffix);
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(response, ApplyUploadPhotoDTO.class));
    }

    @CheckLoginUser
    @PostMapping("/update-photo")
    public ResponseBody<String> updatePhoto(@NotBlank String path) {
        return ResponseBodyBuilder.build(userService.confirmPhoto(UserStpUtil.getLoginId(), path));
    }

    @CheckLoginUser
    @GetMapping("/profile")
    public ResponseBody<UserProfileDTO> profile() {
        UserProfileResponse response = userService.profile(UserStpUtil.getLoginId());
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(response, UserProfileDTO.class));
    }

    @CheckLoginUser
    @PostMapping("/update-profile")
    public ResponseBody<Void> updateProfile(@NotBlank String name,
                                            @NotBlank @Pattern(regexp = "^男$|^女$") String gender) {
        userService.updateProfile(UserStpUtil.getLoginId(), name, gender);
        return ResponseBodyBuilder.build();
    }

    @CheckLoginUser
    @PostMapping("/update-phone")
    public ResponseBody<Void> updatePhone(@NotBlank
                                          @Pattern(regexp = "^((13[0-9])|(14(0|[5-7]|9))|(15([0-3]|[5-9]))|(16(2|[5-7]))|(17[0-8])|(18[0-9])|(19([0-3]|[5-9])))\\d{8}$")
                                          String phone,
                                          @NotBlank @Size(min = 6, max = 6) String code) {
        userService.updatePhone(UserStpUtil.getLoginId(), phone, code);
        return ResponseBodyBuilder.build();
    }
}
