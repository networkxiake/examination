package com.xiaoyao.examination.api.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xiaoyao.examination.api.annotation.CheckLoginAdmin;
import com.xiaoyao.examination.api.annotation.CheckLoginInitAdmin;
import com.xiaoyao.examination.api.controller.dto.admin.AdminLoginDTO;
import com.xiaoyao.examination.api.controller.dto.admin.ApplyUploadPhotoDTO;
import com.xiaoyao.examination.api.controller.dto.admin.SearchAdminDTO;
import com.xiaoyao.examination.api.response.ResponseBody;
import com.xiaoyao.examination.api.response.ResponseBodyBuilder;
import com.xiaoyao.examination.api.util.AdminStpUtil;
import com.xiaoyao.examination.common.interfaces.storage.response.ApplyUploadPhotoResponse;
import com.xiaoyao.examination.common.interfaces.user.AdminService;
import com.xiaoyao.examination.common.interfaces.user.response.AdminLoginResponse;
import com.xiaoyao.examination.common.interfaces.user.response.SearchAdminResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/admin")
public class AdminController {
    @DubboReference
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseBody<AdminLoginDTO> login(@NotBlank String username,
                                             @NotBlank String password) {
        AdminLoginResponse response = adminService.login(username, password);
        AdminLoginDTO dto = new AdminLoginDTO();
        dto.setToken(AdminStpUtil.login(response.getId(), Map.of("isInitAdmin", response.isInitAdmin())));
        dto.setName(response.getName());
        dto.setPhoto(response.getPhoto());
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
    public ResponseBody<Void> changePassword(@NotBlank @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$") String oldPassword,
                                             @NotBlank @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$") String newPassword) {
        adminService.changePassword(AdminStpUtil.getLoginId(), oldPassword, newPassword);
        return ResponseBodyBuilder.build();
    }

    @CheckLoginAdmin
    @PostMapping("/apply-upload-photo")
    public ResponseBody<ApplyUploadPhotoDTO> applyUploadPhoto(@NotBlank String suffix) {
        ApplyUploadPhotoResponse response = adminService.applyUploadPhoto(suffix);
        return ResponseBodyBuilder.build(new ApplyUploadPhotoDTO(response.getPath(), response.getUrl()));
    }

    @CheckLoginAdmin
    @PostMapping("/update-photo")
    public ResponseBody<String> updatePhoto(@NotBlank String path) {
        return ResponseBodyBuilder.build(adminService.updatePhoto(AdminStpUtil.getLoginId(), path));
    }

    @CheckLoginInitAdmin
    @PostMapping("/create")
    public ResponseBody<Void> create(@NotBlank @Pattern(regexp = "^[a-zA-Z0-9]{4,16}$") String username,
                                     @NotBlank @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$") String password,
                                     @NotBlank String name) {
        adminService.createAdmin(username, password, name);
        return ResponseBodyBuilder.build();
    }

    @CheckLoginInitAdmin
    @PostMapping("/delete")
    public ResponseBody<Void> delete(@NotEmpty Long[] ids) {
        List<Long> idsList = Arrays.asList(ids);
        adminService.deleteAdmin(idsList);
        idsList.forEach(AdminStpUtil::logout);
        return ResponseBodyBuilder.build();
    }

    @CheckLoginInitAdmin
    @PostMapping("/search")
    public ResponseBody<SearchAdminDTO> search(long page, long size, String name) {
        SearchAdminResponse response = adminService.searchAdmin(page, size, name);
        return ResponseBodyBuilder.build(BeanUtil.copyProperties(response, SearchAdminDTO.class));
    }
}
