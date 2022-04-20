package com.mercsystem.mapper;

import com.mercsystem.model.TAdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName TAdminRoleMapper.java
 * @Description: 后台用户角色表 Mapper 接口
 * @ProjectName com.mercsystem.mapper
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:06
*/
public interface TAdminRoleMapper extends BaseMapper<TAdminRole> {
    int updRole(@Param("tadminrole") TAdminRole tadminrole);

    int insertRole(@Param("tadminrole") TAdminRole tadminrole);

    long getRole(@Param("tadminrole") TAdminRole tadminrole);

    TAdminRole selcRole(@Param("id") Long id);
}
