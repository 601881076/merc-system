package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhnj.model.TRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/2/24 15:18   
 */
public interface TRoleMapper extends BaseMapper<TRole> {
    List<TRole> getUmsRole(@Param("adminId") Long adminId);
}
