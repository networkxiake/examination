package com.xiaoyao.examination.domain.repository.impl;

import com.xiaoyao.examination.domain.entity.Admin;
import com.xiaoyao.examination.domain.mapper.AdminMapper;
import com.xiaoyao.examination.domain.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public long countAdmin() {
        return adminMapper.selectCount(lambdaQuery());
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
}
