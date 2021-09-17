package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhnj.model.TMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-02-24
 */
public interface TMenuMapper extends BaseMapper<TMenu> {
    List<TMenu> getUmsMenu(@Param("roleId") Long roleId);

    List<TMenu> getUmsMenuByAdminId(@Param("adminId") Long adminId);

}
