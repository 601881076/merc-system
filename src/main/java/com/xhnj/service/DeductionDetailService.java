package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TBatchDtl;
import com.xhnj.pojo.query.DeductionDetailQuery;

/**
 * @program: ums-admin
 * @description: 代扣明细服务类
 * @author: xiel
 * @create: 2021-11-05 17:14
 **/
public interface DeductionDetailService  {
    /**
     *
     * @param tbatchDtl
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage listPage(DeductionDetailQuery tbatchDtl, Integer pageSize, Integer pageNum);
}
