package com.mercsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mercsystem.model.TLog;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName TLogMapper.java
 * @Description: 日志管理mapper
 * @ProjectName com.mercsystem.mapper
 * @Version 1.0
 * @author tanyi
 * @date 2022/4/20 11:06
*/
public interface TLogMapper extends BaseMapper<TLog> {

    IPage listPage(IPage<TLog> page, @Param("tlog") TLog tLog);


}
