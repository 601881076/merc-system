package com.xhnj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TLog;
import org.apache.ibatis.annotations.Param;

/**
* @Description:    日志管理mapper
* @Author:         tan_yi
* @CreateDate:     2021/11/7 16:51
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/7 16:51
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public interface TLogMapper extends BaseMapper<TLog> {

    IPage listPage(IPage<TLog> page, @Param("tlog") TLog tLog);


}
