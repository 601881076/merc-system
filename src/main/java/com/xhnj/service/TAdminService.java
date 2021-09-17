package com.xhnj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhnj.model.TAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lk
 * @since 2021-02-24
 */
public interface TAdminService extends IService<TAdmin> {
    String login(String username, String password);

    TAdmin getAdminByUsername(String username);

    Page getUserPage(String keyword, Integer pageSize, Integer pageNum);

    UserDetails loadUserByUsername(String username);

    int updateAdmin(TAdmin admin);

    int deleteAdmin(Long id);

}
