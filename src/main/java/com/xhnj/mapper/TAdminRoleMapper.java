package com.xhnj.mapper;

import com.xhnj.model.TAdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-10-20
 */
public interface TAdminRoleMapper extends BaseMapper<TAdminRole> {
    int updRole(@Param("tadminrole") TAdminRole tadminrole);

    int insertRole(@Param("tadminrole") TAdminRole tadminrole);

    long getRole(@Param("tadminrole") TAdminRole tadminrole);
}
