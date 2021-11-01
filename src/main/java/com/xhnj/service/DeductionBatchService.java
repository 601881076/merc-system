package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.TBatchNo;
import com.xhnj.pojo.query.DeductionBatchQuery;
import com.xhnj.pojo.query.DisMissBatchQuery;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @Description:    扣款批次审批页面
 * 包含页面查询、批准、拒绝、批量导出报告
* @Author:         tan_yi
* @CreateDate:     2021/11/1 17:24
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/1 17:24
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public interface DeductionBatchService {

    /**
     * 查询
     * @param deductionBatchQuery 批次表
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage page(DeductionBatchQuery deductionBatchQuery, Integer pageSize, Integer pageNum);


}
