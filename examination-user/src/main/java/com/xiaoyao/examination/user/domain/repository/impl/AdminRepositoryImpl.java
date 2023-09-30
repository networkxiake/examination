package com.xiaoyao.examination.user.domain.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyao.examination.user.domain.entity.Admin;
import com.xiaoyao.examination.user.domain.mapper.AdminMapper;
import com.xiaoyao.examination.user.domain.repository.AdminRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

@Repository
public class AdminRepositoryImpl implements AdminRepository {
    private final AdminMapper adminMapper;

    public AdminRepositoryImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public Admin getLoginAdminByUsername(String username) {
        return adminMapper.selectOne(lambdaQuery(Admin.class)
                .select(Admin::getId,
                        Admin::getSalt,
                        Admin::getPassword,
                        Admin::getName,
                        Admin::getPhoto)
                .eq(Admin::getUsername, username));
    }

    @Override
    public long countAdmin(Long id) {
        return adminMapper.selectCount(lambdaQuery(Admin.class)
                .eq(id != null, Admin::getId, id));
    }

    @Override
    public void createInitAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public Admin getSaltAndPasswordById(long id) {
        return adminMapper.selectOne(lambdaQuery(Admin.class)
                .select(Admin::getSalt,
                        Admin::getPassword)
                .eq(Admin::getId, id));
    }

    @Override
    public void updateAdmin(Admin admin) {
        adminMapper.updateById(admin);
    }

    @Override
    public String getPhotoById(long id) {
        return adminMapper.selectOne(lambdaQuery(Admin.class)
                .select(Admin::getPhoto)
                .eq(Admin::getId, id)).getPhoto();
    }

    @Override
    public void createAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return adminMapper.selectCount(lambdaQuery(Admin.class).eq(Admin::getUsername, username)) == 1;
    }

    @Override
    public void deleteAdmin(List<Long> ids, String initAdminUsername) {
        adminMapper.delete(lambdaQuery(Admin.class)
                .in(Admin::getId, ids)
                .ne(Admin::getUsername, initAdminUsername));
    }

    @Override
    public List<Admin> searchAdmin(long page, long size, String name, long[] total, String initAdminUsername) {
        Page<Admin> adminPage = adminMapper.selectPage(Page.of(page, size), lambdaQuery(Admin.class)
                .select(Admin::getId,
                        Admin::getUsername,
                        Admin::getName,
                        Admin::getPhoto,
                        Admin::getCreateTime)
                .eq(StrUtil.isNotBlank(name), Admin::getName, name)
                .ne(Admin::getUsername, initAdminUsername));
        total[0] = adminPage.getTotal();
        return adminPage.getRecords();
    }

    @Override
    public List<String> getPhotoByAdminIds(List<Long> ids, String initAdminUsername) {
        return adminMapper.selectList(lambdaQuery(Admin.class)
                .select(Admin::getPhoto)
                .in(Admin::getId, ids)
                .ne(Admin::getUsername, initAdminUsername)).stream().map(Admin::getPhoto).toList();
    }
}
