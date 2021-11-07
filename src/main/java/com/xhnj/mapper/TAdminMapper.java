package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhnj.model.TAdmin;
import org.apache.ibatis.annotations.Param;

/*
 @Description
 *@author kang.li
 *@date 2021/2/24 15:19   
 */
public interface TAdminMapper extends BaseMapper<TAdmin> {
    int updateUserStatusToDisableByUserName(@Param("admin") TAdmin admin);

}
