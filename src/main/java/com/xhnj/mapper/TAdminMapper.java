package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TAdmin;
import com.xhnj.model.TAdminRoles;
import com.xhnj.pojo.query.DisMissBatchQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
 @Description
 *@author kang.li
 *@date 2021/2/24 15:19   
 */
public interface TAdminMapper extends BaseMapper<TAdmin> {
    int updateUserStatusToDisableByUserName(@Param("admin") TAdmin admin);

    IPage listPage(IPage<TAdminRoles> page, @Param("admin") TAdmin admin);

    int updateByUsername(@Param("admin") TAdmin admin);

    int updateAdminFirstTIme(Long id);
    int updateAdminFirstFlg(Long id);
    int updateAdminStatus(String username);
    List<TAdmin> selectByUsername(@Param("username") String username);
}
