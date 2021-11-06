package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhnj.model.TAdminRole;
import com.xhnj.model.TConfig;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 配置表 Mapper 接口
 * </p>
 *
 * @author xiel
 * @since 2021-11-06
 */
public interface TConfigMapper extends BaseMapper<TConfig> {
    TConfig selctOne(@Param("tadminrole") TConfig configs);

    int updOne(@Param("tadminrole") TConfig configs);
}
