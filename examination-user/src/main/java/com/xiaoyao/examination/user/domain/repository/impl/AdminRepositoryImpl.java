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
    public long countAdmin(Long id) {
        return adminMapper.selectCount(lambdaQuery(Admin.class)
                .eq(id != null, Admin::getId, id));
    }

    @Override
    public List<Admin> findAdminListForSearch(long page, long size, String name, long[] total, String initAdminUsername) {
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
    public Admin findAdminForLogin(String username) {
        return adminMapper.selectOne(lambdaQuery(Admin.class)
                .select(Admin::getId,
                        Admin::getSalt,
                        Admin::getPassword,
                        Admin::getName,
                        Admin::getPhoto)
                .eq(Admin::getUsername, username));
    }

    @Override
    public Admin findAdminForChangePassword(long id) {
        return adminMapper.selectOne(lambdaQuery(Admin.class)
                .select(Admin::getSalt,
                        Admin::getPassword)
                .eq(Admin::getId, id));
    }

    @Override
    public String getPhoto(long id) {
        return adminMapper.selectOne(lambdaQuery(Admin.class)
                .select(Admin::getPhoto)
                .eq(Admin::getId, id)).getPhoto();
    }

    @Override
    public List<String> getPhotoInIds(List<Long> ids, String initAdminUsername) {
        return adminMapper.selectList(lambdaQuery(Admin.class)
                .select(Admin::getPhoto)
                .in(Admin::getId, ids)
                .ne(Admin::getUsername, initAdminUsername)).stream().map(Admin::getPhoto).toList();
    }

    @Override
    public boolean isExistUsername(String username) {
        return adminMapper.selectCount(lambdaQuery(Admin.class).eq(Admin::getUsername, username)) > 0;
    }

    @Override
    public void save(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public void delete(List<Long> ids, String initAdminUsername) {
        adminMapper.delete(lambdaQuery(Admin.class)
                .in(Admin::getId, ids)
                .ne(Admin::getUsername, initAdminUsername));
    }

    @Override
    public void update(Admin admin) {
        adminMapper.updateById(admin);
    }
}
