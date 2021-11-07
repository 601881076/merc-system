package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TLog;

/**
 * @Description:
 * @Author:         tan_yi
 * @CreateDate:     2021/11/7 17:44
 * @UpdateUser:     tan_yi
 * @UpdateDate:     2021/11/7 17:44
 * @UpdateRemark:   修改内容
 * @Version:        1.0
 * @company:        newLand
 */
public interface TLogService{

    /**
     * 分页查询日志表信息
     * @param tlog
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage listPage(TLog tlog, Integer pageSize, Integer pageNum);


}
