package com.xhnj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xhnj.model.TMenu;
import com.xhnj.model.TRole;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author lk
 * @since 2021-02-24
 */
public interface TRoleService extends IService<TRole> {
    Page listPage(String keyword, Integer pageSize, Integer pageNum);

    int create(TRole role);

    int update(TRole role);

    /**
     *
     * @param id 角色id
     * @return
     */
    int delete(Long id, String name);

    /**
     *
     * @param roleId 角色id
     * @param menuIds 菜单id, 多个用逗号分隔
     * @return
     */
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     *
     * @param adminId 后台用户id
     * @return
     */
    List<TRole> getUmsRole(Long adminId);

    /**
     *
     * @param roleId 角色id
     * @return
     */
    List<TMenu> getUmsMenu(Long roleId);

    /**\
     *
     * @param adminId 后台用户id
     * @return
     */
    List<TMenu> getUmsMenuByAdminId(Long adminId);

    List<TRole> listAll();
}
