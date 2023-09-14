package com.xiaoyao.examination.controller;

import com.xiaoyao.examination.annotation.CheckLoginAdmin;
import com.xiaoyao.examination.controller.dto.admin.AdminLoginDTO;
import com.xiaoyao.examination.controller.form.admin.ChangePasswordForm;
import com.xiaoyao.examination.controller.form.admin.ChangePhotoForm;
import com.xiaoyao.examination.controller.form.admin.LoginForm;
import com.xiaoyao.examination.domain.entity.Admin;
import com.xiaoyao.examination.response.ResponseBody;
import com.xiaoyao.examination.response.ResponseBodyBuilder;
import com.xiaoyao.examination.service.AdminService;
import com.xiaoyao.examination.service.StorageService;
import com.xiaoyao.examination.util.AdminStpUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final StorageService storageService;

    @PostMapping("/login")
    public ResponseBody<AdminLoginDTO> login(@Valid @RequestBody LoginForm form) {
        Admin admin = adminService.login(form.getUsername(), form.getPassword());
        AdminLoginDTO dto = new AdminLoginDTO();
        dto.setToken(AdminStpUtil.login(admin.getId()));
        dto.setName(admin.getName());
        dto.setPhoto(admin.getPhoto());
        return ResponseBodyBuilder.build(dto);
    }

    @CheckLoginAdmin
    @PostMapping("/logout")
    public ResponseBody<Object> logout() {
        AdminStpUtil.logout();
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/change-password")
    public ResponseBody<Object> changePassword(@Valid @RequestBody ChangePasswordForm form) {
        adminService.changePassword(AdminStpUtil.getLoginId(), form.getOldPassword(), form.getNewPassword());
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/upload-photo")
    public ResponseBody<Object> uploadPhoto(MultipartFile file) {

        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/change-photo")
    public ResponseBody<Object> changePhoto(@Valid @RequestBody ChangePhotoForm form) {
        adminService.changePhoto(AdminStpUtil.getLoginId(), form.getPath());
        return ResponseBodyBuilder.build();
    }
}
