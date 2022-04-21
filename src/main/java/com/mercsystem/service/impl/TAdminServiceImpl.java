package com.mercsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mercsystem.common.exception.BusinessException;
import com.mercsystem.model.TAdmin;
import com.mercsystem.mapper.TAdminMapper;
import com.mercsystem.service.TAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 后台管理用户表 服务实现类
 * </p>
 *
 * @author tanyi
 * @since 2022-04-21
 */
@Service
public class TAdminServiceImpl extends ServiceImpl<TAdminMapper, TAdmin> implements TAdminService {

    @Autowired
    private TAdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int insert(TAdmin admin) {
        // 查看用户是否已存在
        QueryWrapper<TAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",admin.getPhone());
        TAdmin result = adminMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(result))
            throw new BusinessException("手机号已注册");

        // 用户密码加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        int insert = adminMapper.insert(admin);

        return insert;
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Override
    public TAdmin selectUserInfoByUsername(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        return adminMapper.selectOne(wrapper);
    }

    @Override
    public int update(TAdmin admin) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id", admin.getId());
        return adminMapper.update(admin, wrapper);
    }
}
