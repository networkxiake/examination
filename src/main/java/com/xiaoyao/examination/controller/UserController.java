package com.xiaoyao.examination.controller;

import com.xiaoyao.examination.annotation.CheckLoginUser;
import com.xiaoyao.examination.controller.dto.user.UserLoginDTO;
import com.xiaoyao.examination.controller.dto.user.UserProfileDTO;
import com.xiaoyao.examination.controller.form.user.*;
import com.xiaoyao.examination.domain.entity.User;
import com.xiaoyao.examination.response.ResponseBody;
import com.xiaoyao.examination.response.ResponseBodyBuilder;
import com.xiaoyao.examination.service.StorageService;
import com.xiaoyao.examination.service.UserService;
import com.xiaoyao.examination.util.AdminStpUtil;
import com.xiaoyao.examination.util.UserStpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final StorageService storageService;

    @PostMapping("/send-verification-code")
    public ResponseBody<Void> sendVerificationCode(@Valid @RequestBody SendCodeForm form) {
        userService.sendVerificationCode(form.getPhone());
        return ResponseBodyBuilder.build();
    }

    @PostMapping("/login")
    public ResponseBody<UserLoginDTO> login(@Valid @RequestBody LoginForm form) {
        User user = userService.login(form.getPhone(), form.getCode());
        UserLoginDTO dto = new UserLoginDTO();
        dto.setToken(UserStpUtil.login(user.getId()));
        dto.setPhoto(storageService.getPathDownloadingUrl(user.getPhoto()));
        dto.setName(user.getName());
        return ResponseBodyBuilder.build(dto);
    }

    @CheckLoginUser
    @PostMapping("/logout")
    public ResponseBody<Void> logout() {
        UserStpUtil.logout();
        return ResponseBodyBuilder.build();
    }

    @CheckLoginUser
    @PostMapping("/upload-photo")
    public ResponseBody<String> uploadPhoto(MultipartFile file) {
        return ResponseBodyBuilder.build(storageService.uploadTempUserPhoto(AdminStpUtil.getLoginId(), file));
    }

    @CheckLoginUser
    @PostMapping("/confirm-photo")
    public ResponseBody<String> confirmPhoto(@Valid @RequestBody ChangePhotoForm form) {
        return ResponseBodyBuilder.build(userService.confirmPhoto(UserStpUtil.getLoginId(), form.getPath()));
    }

    @CheckLoginUser
    @GetMapping("/profile")
    public ResponseBody<UserProfileDTO> profile() {
        return ResponseBodyBuilder.build(userService.profile(UserStpUtil.getLoginId()));
    }

    @CheckLoginUser
    @PostMapping("/update-profile")
    public ResponseBody<Void> updateProfile(@Valid @RequestBody UpdateProfileForm form) {
        userService.updateProfile(UserStpUtil.getLoginId(), form.getName(), form.getGender());
        return ResponseBodyBuilder.build();
    }

    @CheckLoginUser
    @PostMapping("/update-phone")
    public ResponseBody<Void> updatePhone(@Valid @RequestBody UpdatePhoneForm form) {
        userService.updatePhone(UserStpUtil.getLoginId(), form.getPhone(), form.getCode());
        return ResponseBodyBuilder.build();
    }
}
