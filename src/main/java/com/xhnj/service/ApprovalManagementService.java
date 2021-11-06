package com.xhnj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhnj.model.*;
import com.xhnj.pojo.query.DeductionBatchQuery;
import com.xhnj.pojo.query.DisMissBatchQuery;
import com.xhnj.pojo.query.WithholdParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @Description:    代扣审批接口
 * 包含页面查询、批准、拒绝、批量导出报告
* @Author:         tan_yi
* @CreateDate:     2021/11/1 17:24
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/1 17:24
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public interface ApprovalManagementService {

    /**
     * 查询
     * @param dismissBatch 批次表
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage batchPage(DisMissBatchQuery dismissBatch, Integer pageSize, Integer pageNum);

    /**
     * 审核员授权取消审批操作
     * @param option 操作类型：1-批准，2-拒绝
     * @param batchNoList
     * @return
     */
    int update(int option, List<String> batchNoList, TAdmin admin);


    /**
     * 导出报告
     * @param disMissBatchQuery
     * @return
     */
    void exportExcel(HttpServletResponse response, DisMissBatchQuery disMissBatchQuery);


}
