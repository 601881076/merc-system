package com.mercsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mercsystem.model.TRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName TRoleMapper.java
 * @Description: 类描述
 * @ProjectName com.mercsystem.mapper
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:06
*/
public interface TRoleMapper extends BaseMapper<TRole> {
    List<TRole> getUmsRole(@Param("adminId") Long adminId);
    List<TRole> getRoleList(@Param("roleId") Long roleId);
}
