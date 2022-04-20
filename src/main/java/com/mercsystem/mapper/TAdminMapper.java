package com.mercsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mercsystem.model.TAdmin;
import com.mercsystem.model.TAdminRoles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName TAdminMapper.java
 * @Description: 系统用户mapper
 * @ProjectName com.mercadmin.mapper
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 09:37
*/
@Repository
public interface TAdminMapper extends BaseMapper<TAdmin> {

    int updateUserStatusToDisableByUserName(@Param("admin") TAdmin admin);

    IPage listPage(IPage<TAdminRoles> page, @Param("admin") TAdmin admin);

    int updateByUsername(@Param("admin") TAdmin admin);

    int updateAdminFirstTIme(Long id);
    int updateAdminFirstFlg(Long id);
    int updateAdminStatus(String username);
    List<TAdmin> selectByUsername(@Param("username") String username);
}
