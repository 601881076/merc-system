package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TDismissBatch;

/**
* @Description:    授权取消列表 接口类
* @Author:         tan_yi
* @CreateDate:     2021/11/6 15:43
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/6 15:43
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public interface AuthorizationCancelService {

    /**
     * 查询
     * @param dismissBatch 批次表
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage listPage(TDismissBatch dismissBatch, Integer pageSize, Integer pageNum);



}
