package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhnj.model.TRoleMenu;

import java.util.List;

/**
 * <p>
 * 角色菜单表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-02-24
 */
public interface TRoleMenuMapper extends BaseMapper<TRoleMenu> {
    int addBatch(List<TRoleMenu> roleMenuList);

    int deleteByRoleId(Long roleId);

}
