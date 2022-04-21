package com.mercsystem.service;

import com.mercsystem.model.TAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台管理用户表 服务类
 * </p>
 *
 * @author tanyi
 * @since 2022-04-21
 */
public interface TAdminService extends IService<TAdmin> {

    /**
     * 新增用户
     * @param admin
     * @return
     */
    int insert(TAdmin admin);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    TAdmin selectUserInfoByUsername(String username);


    /**
     * 更新用户信息
     * @param admin
     * @return
     */
    int update(TAdmin admin);
}
