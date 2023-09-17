package com.xiaoyao.examination.controller;

import com.xiaoyao.examination.annotation.CheckLoginAdmin;
import com.xiaoyao.examination.annotation.CheckLoginInitAdmin;
import com.xiaoyao.examination.controller.dto.admin.AdminLoginDTO;
import com.xiaoyao.examination.controller.dto.admin.SearchAdminDTO;
import com.xiaoyao.examination.controller.form.admin.*;
import com.xiaoyao.examination.domain.entity.Admin;
import com.xiaoyao.examination.response.ResponseBody;
import com.xiaoyao.examination.response.ResponseBodyBuilder;
import com.xiaoyao.examination.service.AdminService;
import com.xiaoyao.examination.util.AdminStpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

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
    public ResponseBody<Void> logout() {
        AdminStpUtil.logout();
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/change-password")
    public ResponseBody<Void> changePassword(@Valid @RequestBody ChangePasswordForm form) {
        adminService.changePassword(AdminStpUtil.getLoginId(), form.getOldPassword(), form.getNewPassword());
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/change-photo")
    public ResponseBody<String> changePhoto(@Valid @RequestBody ChangePhotoForm form) {
        return ResponseBodyBuilder.build(adminService.changePhoto(AdminStpUtil.getLoginId(), form.getPath()));
    }

    @CheckLoginInitAdmin
    @PostMapping("/create")
    public ResponseBody<Void> create(@Valid @RequestBody CreateForm form) {
        adminService.createAdmin(form.getUsername(), form.getPassword(), form.getName());
        return ResponseBodyBuilder.build();
    }

    @CheckLoginInitAdmin
    @PostMapping("/delete")
    public ResponseBody<Void> delete(@Valid @RequestBody DeleteForm form) {
        adminService.deleteAdmin(form.getIds());
        form.getIds().forEach(AdminStpUtil::logout);
        return ResponseBodyBuilder.build();
    }

    @CheckLoginInitAdmin
    @PostMapping("/search")
    public ResponseBody<SearchAdminDTO> search(@Valid @RequestBody SearchForm form) {
        return ResponseBodyBuilder.build(adminService.searchAdmin(form.getPage(), form.getSize(), form.getName()));
    }
}
