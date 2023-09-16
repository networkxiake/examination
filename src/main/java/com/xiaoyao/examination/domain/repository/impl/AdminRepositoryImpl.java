package com.xiaoyao.examination.domain.repository.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyao.examination.domain.entity.Admin;
import com.xiaoyao.examination.domain.mapper.AdminMapper;
import com.xiaoyao.examination.domain.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {
    private final AdminMapper adminMapper;

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
                .eq(name != null, Admin::getName, name)
                .ne(Admin::getUsername, initAdminUsername));
        total[0] = adminPage.getTotal();
        return adminPage.getRecords();
    }
}
