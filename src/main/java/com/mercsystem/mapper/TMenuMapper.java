package com.mercsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mercsystem.model.TMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName TMenuMapper.java
 * @Description: 菜单表 Mapper 接口
 * @ProjectName com.mercsystem.mapper
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:06
*/
public interface TMenuMapper extends BaseMapper<TMenu> {
    List<TMenu> getUmsMenu(@Param("roleId") Long roleId);

    List<TMenu> getUmsMenuByAdminId(@Param("adminId") Long adminId);

    List<TMenu> selectMenuList(@Param("name") String keyword,@Param("parent_id") String parentId,Integer pageSize, Integer pageNum);

}
