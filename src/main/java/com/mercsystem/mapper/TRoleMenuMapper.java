package com.mercsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mercsystem.model.TRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName TRoleMenuMapper.java
 * @Description: 角色菜单表 Mapper 接口
 * @ProjectName com.mercsystem.mapper
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:06
*/
public interface TRoleMenuMapper extends BaseMapper<TRoleMenu> {
    int addBatch(@Param("roleMenuList") List<TRoleMenu> roleMenuList);

    int deleteByRoleId(@Param("roleId") Long roleId);

    List<TRoleMenu> queryMuneByRoleId(@Param("roleId") Long roleId);
}
