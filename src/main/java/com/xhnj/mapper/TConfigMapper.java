package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhnj.model.TAdminRole;
import com.xhnj.model.TConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 配置表 Mapper 接口
 * </p>
 *
 * @author xiel
 * @since 2021-11-06
 */
public interface TConfigMapper extends BaseMapper<TConfig> {
    List<TConfig> selctOne(@Param("config") TConfig configs);
    TConfig selectByFieldName(@Param("config") TConfig configs);
    int updOne(@Param("config") TConfig configs);
}
