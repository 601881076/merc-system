package com.xhnj.service;

import com.xhnj.model.TLog;

/**
* @Description:    日志管理服务类
* @Author:         tan_yi
* @CreateDate:     2021/11/7 16:33
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/7 16:33
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public interface SysLogService {
    /**
     * 日志管理新增
     * @param tLog
     * @return
     */
    int insertAdmin(TLog tLog);

}
