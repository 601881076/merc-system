package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhnj.model.TMsgLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Date 2021/11/11 11:24
 * @Author lk
 **/
public interface TMsgLogMapper extends BaseMapper<TMsgLog> {
    List<TMsgLog> findDistinctBankByMap(@Param("map") Map<String, Object> map);
}
